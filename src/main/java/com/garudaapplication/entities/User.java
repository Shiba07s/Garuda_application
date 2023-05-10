package com.garudaapplication.entities;

import java.util.ArrayList;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.garudaapplication.entities.Post;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
  
@NoArgsConstructor
@Entity
@Table(name="users")
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
	
	@Column(name="user_name",nullable = false, length=100)
    private String name;
    private String email;
    private String password;
    private String about;
    
    @OneToMany(mappedBy="user", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
   private List<Post>posts= new ArrayList<>();
    
    
    

}
