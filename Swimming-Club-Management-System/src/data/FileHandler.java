package data;


import model.*;


import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;


public class FileHandler {
    private final String fileName = "members.csv";
    private final MemberFee memberFee;

    public FileHandler(MemberFee memberFee) {
        this.memberFee = memberFee;
    }


    public void saveMembers(ArrayList<Member> members) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Member m : members) {
                if (m instanceof JuniorMember) {
                    writer.write("JUNIOR;" + m.getMemberId() + ";" + m.getName() + ";" + m.getPhoneNumber() + ";" +
                            m.getBirthYear() + ";" + ((JuniorMember) m).getIsCompetition());
                } else if (m instanceof SeniorMember) {
                    writer.write("SENIOR;" + m.getMemberId() + ";" + m.getName() + ";" + m.getPhoneNumber() + ";" +
                            m.getBirthYear() + ";" + ((SeniorMember) m).getIsCompetition());
                } else {
                    writer.write("PASSIVE;" + m.getMemberId() + ";" + m.getName() + ";" + m.getPhoneNumber() + ";" +
                            m.getBirthYear());
                }
                writer.newLine();
                saveResults(writer, m);
            }
        } catch (IOException e) {
            System.out.println("Fejl ved gemning: " + e.getMessage());
        }
    }


    public ArrayList<Member> loadMembers() {
        ArrayList<Member> members = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                switch (parts[0]) {
                    case "JUNIOR", "SENIOR", "PASSIVE" -> members.add(createMember(parts));
                    case "TRAINING" -> addTrainingResult(parts, members);
                    case "COMPETITION" -> addCompetitionResult(parts, members);
                }
            }
            int maxId = 0;
            for (Member m : members) {
                if (m.getMemberId() > maxId) {
                    maxId = m.getMemberId();
                }
            }
            Member.setNextId(maxId + 1);
        } catch (IOException e) {
            System.out.println("Fejl ved indlæsning: " + e.getMessage());
        }
        return members;
    }


    private void saveResults(BufferedWriter writer, Member m) throws IOException {
        if (m instanceof ActiveMember active) {
            CompetitionSwimmer cs = active.getCompetitionSwimmer();
            if (cs != null) {
                for (TrainingResult tr : cs.getTrainingResults()) {
                    writer.write("TRAINING;" + m.getMemberId() + ";" + tr.getDiscipline() + ";" + tr.getTimeResult() + ";" + tr.getDate());
                    writer.newLine();
                }
                for (CompetitionResult cr : cs.getCompetitionResults()) {
                    writer.write("COMPETITION;" + m.getMemberId() + ";" + cr.getDiscipline() + ";" + cr.getTimeResult() + ";" + cr.getDate() + ";" + cr.getCompetitionName() + ";" + cr.getPlacement());
                    writer.newLine();
                }
            }
        }
    }


    private Member createMember(String[] parts) {
        Member member = null;
        switch (parts[0]) {
            case "JUNIOR" -> {
                JuniorMember junior = new JuniorMember(parts[3], parts[2],
                        Integer.parseInt(parts[4]), Boolean.parseBoolean(parts[5]), memberFee);
                junior.setMemberId(Integer.parseInt(parts[1]));
                member = junior;
            }
            case "SENIOR" -> {
                SeniorMember senior = new SeniorMember(parts[3], parts[2],
                        Integer.parseInt(parts[4]), Boolean.parseBoolean(parts[5]), memberFee);
                senior.setMemberId(Integer.parseInt(parts[1]));
                member = senior;
            }
            case "PASSIVE" -> {
                PassiveMember passive = new PassiveMember(parts[3], parts[2],
                        Integer.parseInt(parts[4]), memberFee);
                passive.setMemberId(Integer.parseInt(parts[1]));
                member = passive;
            }
        }
        return member;
    }


    private void addTrainingResult(String[] parts, ArrayList<Member> members) {
        Member m = findMemberById(Integer.parseInt(parts[1]), members);
        if (m instanceof ActiveMember active) {
            TrainingResult tr = new TrainingResult(
                    Integer.parseInt(parts[3]),
                    LocalDate.parse(parts[4]),
                    Discipline.valueOf(parts[2])
            );
            active.getCompetitionSwimmer().addTrainingResult(tr);
        }
    }


    private void addCompetitionResult(String[] parts, ArrayList<Member> members) {
        Member m = findMemberById(Integer.parseInt(parts[1]), members);
        if (m instanceof ActiveMember active) {
            CompetitionResult cr = new CompetitionResult(
                    Integer.parseInt(parts[3]),
                    LocalDate.parse(parts[4]),
                    Discipline.valueOf(parts[2]),
                    parts[5],
                    Integer.parseInt(parts[6])
            );
            active.getCompetitionSwimmer().addCompetitionResult(cr);
        }
    }


    private Member findMemberById(int id, ArrayList<Member> members) {
        for (Member m : members) {
            if (m.getMemberId() == id) {
                return m;
            }
        }
        return null;
    }
}
