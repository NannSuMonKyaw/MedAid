package com.nsmk.thesis.medaid.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Medicine")
public class Medicine {

    @PrimaryKey(autoGenerate = true)
    // @ColumnInfo(name = "id")
    public int id;

    public String medicineName;

    public String diseaseName;

    public int noOfDosage;

    public String medicineType;

    public String frequency;

    public int consecutiveDays;

    public Medicine(String medicineName, String diseaseName, int noOfDosage, String medicineType, String frequency, int consecutiveDays) {
        this.medicineName = medicineName;
        this.diseaseName = diseaseName;
        this.noOfDosage = noOfDosage;
        this.medicineType = medicineType;
        this.frequency = frequency;
        this.consecutiveDays = consecutiveDays;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public String getDiseaseName() {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    public int getNoOfDosage() {
        return noOfDosage;
    }

    public void setNoOfDosage(int noOfDosage) {
        this.noOfDosage = noOfDosage;
    }

    public String getMedicineType() {
        return medicineType;
    }

    public void setMedicineType(String medicineType) {
        this.medicineType = medicineType;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public int getConsecutiveDays() {
        return consecutiveDays;
    }

    public void setConsecutiveDays(int consecutiveDays) {
        this.consecutiveDays = consecutiveDays;
    }



    public static Medicine[] populateData() {
        return new Medicine[]{

                new Medicine("Milk of Magnesia(magnesium hydroxide)","Constipation",4,"spoon","hs",2),
                new Medicine("Fiber diet","Constipation",0,"diet","am",2),

                new Medicine("Ciprofloxacin 500mg","Diarrhoea",1,"tablet","bd",3),
                new Medicine("ORS(oral rehydration salts)","Diarrhoea",2,"litre","adlib",2),

                new Medicine("Aceclofenac 100mg","Muscle pain",1,"tablet","bd",2),
                new Medicine("Paracetamol 500mg","Muscle pain",1,"tablet","tds",2),
                new Medicine("Pantoprazole 40mg ","Muscle pain",1,"tablet","bd",2),

                new Medicine("Paracetamol 500mg","Headache",1,"tablet","tds",2),
                new Medicine("Neurobion","Headache",1,"tablet","bd",2),

                new Medicine("Digene","Indigestion",1,"tablet","tds",2),

                new Medicine("Pantoprazole 40mg","Stomach pain",1,"tablet","bd",2),
                new Medicine("Buscopan 10mg","Stomach pain",1,"tablet","bd",2),

                new Medicine("ORS(oral rehydration salts)","Dehydration",2,"litre","adlib",2),

                new Medicine("Chlorpheniramine 2mg","Runny nose",1,"tablet","tds",2),
                new Medicine("Paracetamol 500mg","Runny nose",1,"tablet","tds",2),
                new Medicine("Amoxicillin 500mg","Runny nose",1,"tablet","tds",2),

                new Medicine("Chericof flu plus","Congestion",1,"tablet","bd",2),
                new Medicine("Paracetamol 500mg","Congestion",1,"tablet","tds",2),
                new Medicine("Amoxicillin 500mg","Congestion",1,"tablet","tds",2),


                new Medicine("Hepasal","Alcoholic Hepatitis",1,"tablet","bd",2),
                new Medicine("Becozinc","Alcoholic Hepatitis",1,"tablet","od",2),

                new Medicine("Cetirizine 5mg","Allergy",1,"tablet","bd",2),
                new Medicine("Ranitidine","Allergy",1,"tablet","bd",2),
                new Medicine("Dexamethasone 8mg","Allergy",1,"tablet","tds",2),

                new Medicine("Aceclofenac 100mg","Arthritis",1,"tablet","bd",2),
                new Medicine("Paracetamol 500mg","Arthritis",1,"tablet","tds",2),
                new Medicine("Pantoprazole 40mg ","Arthritis",1,"tablet","bd",2),

                new Medicine("Inhaler seretide","Bronchial Asthma",1,"puff","bd",2),
                new Medicine("Oral Montelukast 10mg","Bronchial Asthma",1,"tablet","od",2),

                new Medicine("Aceclofenac 100mg","Cervical Spondylosis",1,"tablet","bd",2),
                new Medicine("Paracetamol 500mg","Cervical Spondylosis",1,"tablet","tds",2),
                new Medicine("Pantoprazole 40mg ","Cervical Spondylosis",1,"tablet","bd",2),
                new Medicine("Vit b denk","Cervical Spondylosis",1,"tablet","od",2),

                new Medicine("Metformin 500mg","Diabetes",1,"tablet","bd",2),
                new Medicine("Gliclazide 30mg","Diabetes",1,"tablet","od",2),

                new Medicine("ORS(oral rehydration salts)","Gastroenteritis",2,"litre","adlib",2),
                new Medicine("Ciprofloxacin 500mg","Gastroenteritis",1,"tablet","bd",2),
                new Medicine("Zinc 100mg","Gastroenteritis",1,"tablet","od",2),

                new Medicine("Digene","GERD",1,"tablet","tds",2),
                new Medicine("Pantoprazole 40mg","GERD",1,"tablet","bd",2),

                new Medicine("Amlodipine 5mg","Hypertension",1,"tablet","od",2),

                new Medicine("Levothyroxine 12.5mg","Hypothyroidism",1,"tablet","od",2),

                new Medicine("Coartem","Malaria",4,"tablet","bd",3),
                new Medicine("Primaquine","Malaria",2,"tablet","od",14),

                new Medicine("Nuflam","Osteoarthritis",1,"tablet","bd",2),
                new Medicine("Aceclofenac 100mg","Osteoarthritis",1,"tablet","bd",2),
                new Medicine("Pantoprazole 40mg ","Osteoarthritis",1,"tablet","bd",2),

                new Medicine("Carbimazole 15mg","Hyperthyroidism",1,"tablet","od",2),
                new Medicine("Propranolol 40mg","Hyperthyroidism",1,"tablet","bd",2),

                new Medicine("Pantoprazole 40mg ","Peptic Ulcers",1,"tablet","bd",2),
                new Medicine("Amoxicillin 1g","Peptic Ulcers",1,"tablet","tds",2),
                new Medicine("Clarithromycin 500mg","Peptic Ulcers",1,"tablet","bd",2),

                new Medicine("Co amoxiclav 625mg","Pneumonia",1,"tablet","tds",2),
                new Medicine("Paracetamol 500mg","Pneumonia",1,"tablet","tds",2),
                new Medicine("Acetylcysteine 100mg","Pneumonia",1,"tablet","bd",2),

                new Medicine("Levofloxacin 500mg","Urinary Tract Infection",1,"tablet","bd",2),
                new Medicine("Paracetamol 500mg","Urinary Tract Infection",1,"tablet","tds",2),

                new Medicine("Ciprofloxacin 500mg","Typhoid",1,"tablet","bd",2),
                new Medicine("Tepid sponging","Typhoid",0,"tepidSponging","od",2),

                new Medicine("Beclomethasone ointment","Psoriasis",0,"ointment","tds",2),
                new Medicine("Vit b denk","Psoriasis",1,"tablet","od",2),

                new Medicine("sumatriptan 50mg","Migraine",1,"tablet","bd",2),
                new Medicine("Paracetamol 500mg","Migraine",1,"tablet","tds",2),

                new Medicine("Co amoxiclav 625mg"," Impetigo",1,"tablet","tds",2),
                new Medicine("Becozinc "," Impetigo",1,"tablet","od",2),

                new Medicine("Itraconazole 200mg","Fungal Infection",1,"tablet","bd",10),
                new Medicine("Ketoconazole ointment","Fungal Infection",0,"ointment","tds",2),

                new Medicine("Prochlorperazine 10mg","Paroxysmal Positional Vertigo",1,"tablet","tds",2),
                new Medicine("Vit b denk","Paroxysmal Positional Vertigo",1,"tablet","od",2),

                new Medicine("Cetrizine 5mg","Chickenpox",1,"tablet","bd",2),
                new Medicine("Amoxicillin 500mg","Chickenpox",1,"tablet","tds",2),
                new Medicine("Becozinc ","Chickenpox",1,"tablet","od",2),

                new Medicine("Clindamycin with benzoyl peroxide Gel","Acne",0,"ointment","adlib",2),
                new Medicine("tretinoin ointment","Acne",0,"ointment","hs",2),

                new Medicine("Tenofovir 300mg","Hepatitis B",1,"tablet","od",7),
                new Medicine("Hepasal","Hepatitis B",1,"tablet","od",2),
                new Medicine("Vit b denk","Hepatitis B",1,"tablet","od",2),

                new Medicine("Chlorpheniramine 2mg","Common Cold",1,"tablet","tds",2),
                new Medicine("Paracetamol 500mg","Common Cold",1,"tablet","tds",2),
                new Medicine("Amoxicillin 500mg","Common Cold",1,"tablet","tds",2),

        };
    }
}