import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Vote {
    public static String value = "Vote";

    private final Map<Integer, Map<Integer, Integer>> count = new HashMap<Integer, Map<Integer, Integer>>();
    private final Map<Integer, Map<Integer, String>> answers = new HashMap<Integer, Map<Integer, String>>();
    private final Map<Integer, String> questions = new HashMap<Integer, String>();
    private final Map<Integer, Set<Integer>> hasVoted = new HashMap<Integer, Set<Integer>>();
    private static int questionCount = 1;

    private String getRes(int questionNum){
        StringBuilder rt = new StringBuilder(new String(questions.get(questionNum)));
        rt.append(". question number: ").append(Integer.toString(questionNum)).append("\n");
        for(int i=0;i<answers.get(questionNum).size();i++){
            rt.append(Integer.toString(i+1)).append(": ");
            rt.append(answers.get(questionNum).get(i + 1)).append(" ").append(Integer.toString(count.get(questionNum).get(i + 1))).append("\n");
        }
        return(rt.toString());
    }

    public String action(String s, Integer personId){
        String[] split = s.split("\n");
        String[] firstSplit = split[0].split(" ");
        if(firstSplit[0].equals("new")){
            questions.put(questionCount, split[1]);
            count.put(questionCount, new HashMap<Integer, Integer>());
            answers.put(questionCount, new HashMap<Integer, String>());
            hasVoted.put(questionCount, new HashSet<Integer>());
            for(int i=2;i<split.length;i++){
                answers.get(questionCount).put(i-1, split[i]);
                count.get(questionCount).put(i-1, 0);
            }
            return(getRes(questionCount++));

        }
        if(firstSplit[0].equals("vote")){
            int questionNum = Integer.parseInt(split[1]);
            int voteNum = Integer.parseInt(split[2]);
            if(questionNum>=questionCount || count.get(questionNum).size()<voteNum) {
                return(null);
            }
            if(!hasVoted.get(questionNum).contains(personId)){
                count.get(questionNum).put(voteNum, count.get(questionNum).get(voteNum)+1);
                hasVoted.get(questionNum).add(personId);
            }
            return(getRes(questionNum));
        }
        return(null);
    }

    public Vote(){

    }

}
