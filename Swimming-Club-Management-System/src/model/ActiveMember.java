package model;

public abstract class ActiveMember extends Member {
    private boolean isCompetition;
    private CompetitionSwimmer competitionSwimmer;

    public ActiveMember(String phoneNumber, String name, int birthYear, boolean isCompetition, MemberFee memberFee) {
        super(phoneNumber, name, birthYear, true, memberFee);
        this.isCompetition = isCompetition;
        if (isCompetition) {
            this.competitionSwimmer = new CompetitionSwimmer();
        } else {
            this.competitionSwimmer = null;
        }
    }

    public CompetitionSwimmer getCompetitionSwimmer() {
        return competitionSwimmer;
    }

    public void setCompetitionSwimmer(CompetitionSwimmer competitionSwimmer) {
        this.competitionSwimmer = competitionSwimmer;
    }

    public boolean getIsCompetition() {
        return isCompetition;
    }
}
