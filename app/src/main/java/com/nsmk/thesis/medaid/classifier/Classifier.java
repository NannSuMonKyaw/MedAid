package com.nsmk.thesis.medaid.classifier;


import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;

import org.tensorflow.lite.Interpreter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class Classifier {
      private Interpreter interpreter;
    private List<String> lableList;
    private int MAX_RESULTS = 10;
    private float THRESHOLD = (float) 0.2;



    public Classifier(AssetManager assetManager, String modelPath, String labelPath) {
        Interpreter.Options options = new Interpreter.Options();
        options.setNumThreads(5);
        options.setUseNNAPI(true);
        interpreter= new Interpreter(loadModelFile(assetManager,modelPath),options);
        lableList=loadLabelList(assetManager,labelPath);

    }

    private MappedByteBuffer loadModelFile(AssetManager assetManager,String modelPath)  {
        MappedByteBuffer tfliteModel=null;
        try {
            AssetFileDescriptor fileDescriptor = assetManager.openFd(modelPath);
            FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
            FileChannel fileChannel = inputStream.getChannel();
            long startOffset = fileDescriptor.getStartOffset();
            long declareLength = fileDescriptor.getDeclaredLength();
            tfliteModel=fileChannel.map(FileChannel.MapMode.READ_ONLY,startOffset,declareLength);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return tfliteModel;
    }

//    private List<String> loadLabelList(AssetManager assetManager, String labelPath) {
//        List<String> labelList = null;
//        BufferedReader reader = null;
//        try {
//            reader = new BufferedReader(
//                    new InputStreamReader(assetManager.open(labelPath)));
//
//            // do reading, usually loop until end of file reading
//            String mLine;
//            while ((mLine = reader.readLine()) != null) {
//                //process line
//                labelList.add(mLine);
//
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return labelList;
//    }


    private List<String> loadLabelList(AssetManager assetManager, String labelPath) {
        List<String> labelList=new ArrayList<String>();

        try {
            InputStream is = assetManager.open(labelPath);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line=reader.readLine() )!= null) {
                labelList.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return labelList;
    }
//     return assetManager.open(labelPath).bufferedReader().useLines { it.toList() }

    public List<Recognition> diagnose(float[] symptoms){

        float[] inputVal=new float[107];
        for(int i=0;i<107;i++) {
            inputVal[i] = symptoms[i];
        }

        float[][] output = new float[1][lableList.size()];
        interpreter.run(inputVal,output);
        return  getSortedResult(output);

    }

    private List<Recognition> getSortedResult(float[][] labelProbArray){
        PriorityQueue<Recognition> priorityQueue=new PriorityQueue<Recognition>(MAX_RESULTS,new RecognitionComparator());

        for (int i=0;i<lableList.size();i++) {
            float confidence = labelProbArray[0][i];

            if (confidence >= THRESHOLD) {
                String label=lableList.get(i);
                Recognition recognition=new Recognition(label,confidence);
                priorityQueue.add(recognition);

            }
        }
        List<Recognition> recognitions=new ArrayList<Recognition>();
        while(!priorityQueue.isEmpty()){
            recognitions.add( priorityQueue.poll());
        }

        return recognitions;
    }
    public class Recognition{
        private String disease;
        private float confidence;

        public Recognition(  String label, float confidence) {
            this.disease=label;
            this.confidence=confidence;
        }

        public String getResult(){
            return  "Disease = "+disease+" , Confidence = "+ confidence;
        }

        public String getDisease() {
            return disease;
        }

        public float getConfidence() {
            return confidence;
        }
    }


    public class RecognitionComparator implements Comparator<Recognition>{

        @Override
        public int compare(Recognition o1, Recognition o2) {
            if(o1.confidence<o2.confidence)
                return 1;
            else if(o1.confidence>o2.confidence)
                return -1;
            return 0;
        }
    }


}
