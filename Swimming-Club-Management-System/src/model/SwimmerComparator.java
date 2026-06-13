package model;

import java.util.Comparator;

public class SwimmerComparator implements Comparator<Member> {
    private Discipline discipline;
    private boolean isCompetition;

    public SwimmerComparator(Discipline discipline, boolean isCompetition) {
        this.discipline = discipline;
        this.isCompetition = isCompetition;
    }

    @Override
    public int compare(Member o1, Member o2) {
        int time1 = getTime(o1);
        int time2 = getTime(o2);
        return Integer.compare(time1, time2);
    }

    private int getTime(Member member) {
        if (member instanceof  ActiveMember) {
            ActiveMember active = (ActiveMember) member;
            CompetitionSwimmer cs = active.getCompetitionSwimmer();
            if (cs != null) {
                if (isCompetition) {
                    return cs.getBestCompetitionResults(discipline);
                }else {
                    return cs.getBestTrainingResults(discipline);
                }
            }
        }
        return Integer.MAX_VALUE;
    }
}
