package com.IMM2020.imm_project;

public class Question {

    public String[] questions = {

            "Addition of motor units to increase the force of contraction in muscles is called what?",
            "An EMG Signal is:",
            "Consists of a motor neuron and all the skeletal muscle fibers with which it synapses is:",
            "Nerve cells that Transmit information to muscles are called what?",
            "The Conduction velocity of motor nerves is approximately:",
            "Voluntary muscles can be found in: ",
            "Which protein makes up the contactable part of muscles?",
            "What are muscles covered in externally?",
            "What is Myopathy?",
            "Which is a muscle relaxant?"

    };

    public String[][] choices = {
            {"Recruitment",
                    "Extensor",
                    "Muscle twitch",
                    "Flexing"},
            {"Summation of all electrical potentials generated by excitable cells",
                    "Summation of all neuro Contractions of muscles ",
                    "The inability of muscle to continue to generate or sustain tension",
                    "The electric pulse moving through the nerves.",
            },
            {"EMG Signal",
                    "Motor Unit",
                    "Recruitment",
                    "Sarcomere"},
            {"Sensory neurons",
                    "Motor neurons",
                    "Interneurons",
                    "Chemical neurons"},
            {"10m/s",
                    "2m/s",
                    "50m/s",
                    "15m/s"},
            {
                    "Skeletal system",//X
                    "Circulatory system",
                    "Digestive track",
                    "Respiratory system"
            },
            {
                    "Actin",//X
                    "Azetylcholin",
                    "Myelin",
                    "Sarcomer"
            },
            {
                    "Sarcomere", "Fascicle", "Transversal tubuli", "Epimysium"
            },
            {
                    "Failure of the muscle mitochondria", "Failure of the neurotransmitters", "Failure of the neurons", "Failure of the muscle fibers"
            },
            {
                    "Propofol", "Piritramid", "Lorezepam", "Fentanyl"
            }
    };

    public String[] correctAnswer = {
            "Recruitment",
            "Summation of all electrical potentials generated by excitable cells",
            "Motor Unit",
            "Motor neurons",
            "50m/s",
            "Skeletal system",
            "Actin",
            "Epimysium",
            "Failure of the muscle fibers",
            "Lorezepam"};


    public String getQuestion(int a) {

        return questions[a];
    }

    public String getChoice1(int a) {

        return choices[a][0];
    }

    public String getChoice2(int a) {

        return choices[a][1];
    }

    public String getChoice3(int a) {

        return choices[a][2];
    }

    public String getChoice4(int a) {

        return choices[a][3];
    }

    public String getCorrectAnswer(int a) {
        return correctAnswer[a];
    }
}
