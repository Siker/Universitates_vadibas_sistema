package lv.university.services;

import lv.university.entities.Course;
import lv.university.entities.Schedule;

import java.util.ArrayList;
import java.util.List;

public class ScheduleService {
    private final List<Schedule> schedules = new ArrayList<>();
    private int nextId = 1;

    public Schedule addSchedule(Course course, String dayOfWeek, String startTime,
                                String endTime, String room) {
        Schedule schedule = new Schedule(nextId++, course, dayOfWeek, startTime, endTime, room);
        schedules.add(schedule);
        course.addSchedule(schedule);
        return schedule;
    }

    public List<Schedule> getScheduleByCourse(Course course) {
        List<Schedule> result = new ArrayList<>();
        for (Schedule s : schedules) {
            if (s.getCourse().getId() == course.getId()) {
                result.add(s);
            }
        }
        return result;
    }

    public List<Schedule> getAllSchedules() {
        return new ArrayList<>(schedules);
    }

    public boolean removeSchedule(int id) {
        return schedules.removeIf(s -> s.getId() == id);
    }
}
