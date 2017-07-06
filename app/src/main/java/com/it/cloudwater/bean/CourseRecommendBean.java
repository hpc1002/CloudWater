package com.it.cloudwater.bean;

import java.util.ArrayList;

/**
 * Created by hpc on 2017/4/12.
 */

public class CourseRecommendBean {
    public ArrayList<RecommendData> data;
//    public RecommendMeta meta;

    public class RecommendData {
        public Course course;

        public class Course {
            public CourseData data;

            public class CourseData {
                public int id;
                public String name;
                public String type;
                public String description;
                public int show_number;
                public double price;
                public double discount_price;
                public CourseImage image;

                public class CourseImage {
                    public CourseImageData data;

                    public class CourseImageData {
                        public String path;
                        public String duration;
                    }
                }
            }
        }
    }
//
//    public class RecommendMeta {
//        public Pagination pagination;
//
//        public class Pagination {
//            public int total;
//            public int count;
//            public int per_page;
//            public int total_pages;
//            public Links links;
//
//            public class Links {
//                public String next;
//                public String previous;
//            }
//        }
//    }
}
