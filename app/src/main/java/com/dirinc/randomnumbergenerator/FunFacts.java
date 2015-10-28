package com.dirinc.randomnumbergenerator;

public class FunFacts {

    public static void main (String args []) {
        FunFacts go = new FunFacts();
        go.getFactoid();
    }

    public String[] getFactoid() {
        NumberActivity retriever = new NumberActivity();

        int stashedRecord = retriever.getRecord();
        int factoidRecord = stashedRecord + 1;
        String lessThanString;
        String factoidInfo = "";

        String[] factoidArr = new String[2];

        if((stashedRecord < 500000) && (stashedRecord > 400000)) {
            lessThanString = "Less than " + factoidRecord + "! You just beat about a 50% chance.";
            factoidInfo = "That's the odds of getting diagnosed with cancer!";
        }
        else if((stashedRecord < 400000) && (stashedRecord > 300000)) {
            lessThanString = "Less than " + factoidRecord + "! You just beat about a 40% chance.";
            factoidInfo = "That's the odds of a celebrity marriage lasting a lifetime!";
        }
        else if((stashedRecord < 300000) && (stashedRecord > 200000)) {
            lessThanString = "Less than " + factoidRecord + "! You just beat about a 30% chance.";
            factoidInfo = "That's the odds that any given president attended Harvard!";
        }
        else if((stashedRecord < 200000) && (stashedRecord > 100000)) {
            lessThanString = "Less than " + factoidRecord + "! You just beat about a 20% chance.";
            factoidInfo = "That's the odds of having a stroke!";
        }
        else if((stashedRecord < 100000) && (stashedRecord > 75000)) {
            lessThanString = "Less than " + factoidRecord + "! You just beat about a 10% chance.";
            factoidInfo = "That's the odds of getting the flu this year!";
        }
        else if((stashedRecord < 75000) && (stashedRecord > 50000)) {
            lessThanString = "Less than " + factoidRecord + "! You just beat about a 7.5% chance.";
            factoidInfo = "That's the odds of getting accepted to MIT!";
        }
        else if((stashedRecord < 50000) && (stashedRecord > 25000)) {
            lessThanString = "Less than " + factoidRecord + "! You just beat about a 5% chance.";
            factoidInfo = "That's the odds of being the victim of a serious crime in your lifetime!";
        }
        else if((stashedRecord < 25000) && (stashedRecord > 10000)) {
            lessThanString = "Less than " + factoidRecord + "! You just beat about a 2.5% chance.";
            factoidInfo = "That's the odds of being born with 11 toes!";
        }
        else if((stashedRecord < 10000) && (stashedRecord > 5000)) {
            lessThanString = "Less than " + factoidRecord + "! You just beat about a 1% chance.";
            factoidInfo = "That's the odds of being on a plane with a drunken pilot!";
        }
        else if((stashedRecord < 5000) && (stashedRecord > 4000)) {
            lessThanString = "Less than " + factoidRecord + "! You just beat about a 0.5% chance.";
            factoidInfo = "That's the odds of being audited by the IRS!";
        }
        else if((stashedRecord < 4000) && (stashedRecord > 3000)) {
            lessThanString = "Less than " + factoidRecord + "! You just beat about a 0.4% chance.";
            factoidInfo = "That's the odds of dating a millionaire!";
        }
        else if((stashedRecord < 3000) && (stashedRecord > 2000)) {
            lessThanString = "Less than " + factoidRecord + "! You just beat about a 0.3% chance.";
            factoidInfo = "That's the odds of dying in a car accident";
        }
        else if((stashedRecord < 2000) && (stashedRecord > 1000)) {
            lessThanString = "Less than " + factoidRecord + "! You just beat about a 0.2% chance.";
            factoidInfo = "That's the odds of catching a ball at a major league baseball game";
        }
        else if((stashedRecord < 1000) && (stashedRecord > 750)) {
            lessThanString = "Less than " + factoidRecord + "! You just beat about a 0.1% chance.";
                factoidInfo = "That's the odds of being killed while crossing the street";
        } else if((stashedRecord < 750) && (stashedRecord > 500)) {
            lessThanString = "Less than " + factoidRecord + "! You just beat about a 0.075% chance.";
            factoidInfo = "That's the odds of dying from accidental poisoning!";
        }
        else if((stashedRecord < 500) && (stashedRecord > 400)) {
            lessThanString = "Less than " + factoidRecord + "! You just beat about a 0.05% chance.";
            factoidInfo = "That's the odds of dying from any injury this year!";
        }
        else if((stashedRecord < 400) && (stashedRecord > 300)) {
            lessThanString = "Less than " + factoidRecord + "! You just beat about a 0.04% chance.";
            factoidInfo = "That's the odds of ";
        } else if((stashedRecord < 200) && (stashedRecord > 100)) {
            lessThanString = "Less than " + factoidRecord + "! You just beat about a 0.02% chance.";
            factoidInfo = "That's the odds of an amateur golfer getting an  hole in one!";
        }
        else if((stashedRecord < 100) && (stashedRecord > 75)) {
            lessThanString = "Less than " + factoidRecord + "! You just beat about a 0.01% chance.";
            factoidInfo = "That's the odds of winning the military Medal of Honor";
        }
        else if((stashedRecord < 75) && (stashedRecord > 50)) {
            lessThanString = "Less than " + factoidRecord + "! You just beat about a 0.0075% chance.";
            factoidInfo = "That's the odds of being killed by a hippo";
        }
        else if((stashedRecord < 50) && (stashedRecord > 25)) {
            lessThanString = "Less than " + factoidRecord + "! You just beat about a 0.005% chance.";
            factoidInfo = "That's the odds of slipping and dying in the shower!";
        }
        else if((stashedRecord < 25) && (stashedRecord > 10)) {
            lessThanString = "Less than " + factoidRecord + "! You just beat about a 0.0025% chance.";
            factoidInfo = "That's the odds of dying due to sharp objects!";
        }
        else if((stashedRecord < 10) && (stashedRecord > 1)) {
            lessThanString = "Less than " + factoidRecord + "! You just beat about a 0.0001% chance.";
            factoidInfo = "That's the odds of getting struck by lightning!";
        }
        else if(stashedRecord == 1) {
            lessThanString = "much wow. very sad.";
        }
        else {
            lessThanString = "Less than something big";
            factoidInfo = "I don't know the odds of that.";
        }

        factoidArr[0] = lessThanString;
        factoidArr[1] = factoidInfo;

        return factoidArr;

/*
        TextView factoid = (TextView) findViewById(R.id.factoid);
        if(factoid != null) factoid.setText(lessThanString);

        TextView factoidDetail = (TextView) findViewById(R.id.factoidDetail);
        if(factoidDetail != null) factoidDetail.setText(factoidInfo);
*/
    }
}
