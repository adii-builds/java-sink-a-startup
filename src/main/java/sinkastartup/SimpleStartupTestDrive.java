package sinkastartup;

public class SimpleStartupTestDrive {
    public static void main (String[] args){
        SimpleStartup dot = new SimpleStartup();


        int[] locations = {3,4,5};
        dot.setLocationCells(locations);


        int userGuess = 3;
        String result = dot.checkYourself(userGuess);


        String testResult = "failed";
        if (result.equals("hit")){
            testResult = "passed";
        }

        System.out.println(testResult);
    }
}
