package dao;

import allentity.Course;

import jakarta.persistence.*;

import java.util.*;

public class CourseDAO {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("School_PU");

    public Course addCourse(Course course) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(em.merge(course));
            em.getTransaction().commit();
        } catch (RuntimeException e){
            System.out.println("Data record already exists!");
        }
        em.close();
        return course;
    }

    public void deleteCourse(int id) {
        EntityManager em = emf.createEntityManager();
        Course course = em.find(Course.class, id);
        if(course != null) {
            em.getTransaction().begin();
            em.remove(em.merge(course));
            em.getTransaction().commit();
        }
        em.close();
    }

    public void deleteCourse(Course course) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.remove(em.merge(course));
        em.getTransaction().commit();
        em.close();
    }

    public Course findCourse(int id) {
        EntityManager em = emf.createEntityManager();
        Course course = null;
        try {
            course = em.find(Course.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(course != null) {
            em.close();
            return course;
        }
        em.close();
        return null;
    }

    public Course findCourse(String courseName) {
        EntityManager em = emf.createEntityManager();
        TypedQuery< Course > query = em.createNamedQuery("Course.findByCourseTitle", Course.class);
        query.setParameter("courseTitle", courseName);
        Course course = null;
        try {
            course = query.getSingleResult();
        } catch (Exception e) {
            course=null;
        }
        if(course != null) {
            em.close();
            return course;
        }
        em.close();
        return null;
    }

    public void updateCourse(Course course) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(course);
        em.getTransaction().commit();
        em.close();
    }

    public List< Course > findCourseByName(String courseName) {
        EntityManager em = emf.createEntityManager();
        TypedQuery< Course > query = em.createNamedQuery("Course.findByCourseTitle", Course.class);
        query.setParameter("courseTitle", courseName);
        List< Course > resultList = (List< Course >) query.getResultList();
        em.close();
        return resultList;
    }

    public List< Course > showAllCourses() {
        EntityManager em = emf.createEntityManager();
        TypedQuery< Course > query = em.createNamedQuery("Course.findAll", Course.class);
        List< Course > resultList = null;
        try {
            resultList = (List< Course >) query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        em.close();
        return resultList;
    }


}
