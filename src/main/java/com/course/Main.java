package com.course;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@SpringBootApplication
@RestController
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
    
    @GetMapping("/greet")
    public GreetResponse greet(){
        GreetResponse response = new GreetResponse(
                "Hello",
                List.of("Java", "C++", "Typescript"),
                new Person("Ann")
                );
        return response;
    }

    record Person(String name){

    }
    record GreetResponse(
            String greet,
            List<String> programmingLanguages,
            Person person
    ){}

//    class GreetResponse{
//        private final String greet;
//
//        GreetResponse(String greet){
//            this.greet = greet;
//        }
//
//        public String getGreet(){
//            return greet;
//        }
//
//        @Override
//        public String toString(){
//            return "GreetResponse{" +
//                    "greet='" + greet + '\'' + '}';
//        }
//
//        @Override
//        public boolean equals(Object obj) {
//            if(this == obj) return true;
//            if(obj == null || getClass() != obj.getClass()) return false;
//            GreetResponse that = (GreetResponse) obj;
//            return Objects.equals(greet, that.greet);
//        }
//
//        @Override
//        public int hashCode(){
//            return Objects.hash(greet);
//        }
//    }

}
