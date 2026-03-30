package lv.university.entities;

public class Schedule {
    private int id;
    private Course course;
    private String dayOfWeek;
    private String startTime;
    private String endTime;
    private String room;

    public Schedule(int id, Course course, String dayOfWeek, String startTime, String endTime, String room) {
        this.id = id;
        this.course = course;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
        this.room = room;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }

    public String getDayOfWeek() { return dayOfWeek; }
    public void setDayOfWeek(String dayOfWeek) { this.dayOfWeek = dayOfWeek; }

    public String getStartTime() { return startTime; }
    public void setStartTime(String startTime) { this.startTime = startTime; }

    public String getEndTime() { return endTime; }
    public void setEndTime(String endTime) { this.endTime = endTime; }

    public String getRoom() { return room; }
    public void setRoom(String room) { this.room = room; }

    @Override
    public String toString() {
        return "Kurss: " + course.getName() + ", Diena: " + dayOfWeek
                + ", Laiks: " + startTime + " - " + endTime + ", Telpa: " + room;
    }
}
