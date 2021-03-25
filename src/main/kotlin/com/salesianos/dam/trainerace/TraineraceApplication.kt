package com.salesianos.dam.trainerace

import com.salesianos.dam.trainerace.models.Comment
import com.salesianos.dam.trainerace.models.Dieta
import com.salesianos.dam.trainerace.models.Trainer
import com.salesianos.dam.trainerace.models.User
import com.salesianos.dam.trainerace.services.CommentService
import com.salesianos.dam.trainerace.services.DietaService
import com.salesianos.dam.trainerace.services.TrainerService
import com.salesianos.dam.trainerace.services.UserService
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import java.time.LocalDate

@SpringBootApplication
class TraineraceApplication {

	@Bean
	fun init(userService: UserService, trainerService: TrainerService, dietaService: DietaService, commentService: CommentService) = CommandLineRunner {

		var usuario1 = User("ByPablo27", "12345", "pablo@gmail.com", "H", "1/1/1", "Pablo", "Silva", "USER")
		var trainer1 = Trainer("Daniel","Leiva","Hombre","Padel",25,"Sevilla",6.50,"Muy bueno", "25/02/1995","TRAINER")
		var trainer2 = Trainer("Pablo","Silva","Hombre","Baloncesto",20,"Sevilla",4.00,"Muy bueno", "27/05/2000","TRAINER")
		var trainer3 = Trainer("Laura","Silva","Mujer","Gimnasia",28,"Sevilla",5.40,"Muy bueno", "09/03/1990","TRAINER")
		var trainer4 = Trainer("Daniel","Ceballos","Hombre","Kárate",30,"Sevilla",7.50,"Muy bueno", "25/02/1989","TRAINER")
		var dieta1 = Dieta("Hipercalorica",2000.0, "Esta es una dieta hipercalorica", "7 semanas", "DIETA")
		var dieta2 = Dieta("Proteica",2000.0, "Esta es una dieta hipercalorica", "7 semanas", "DIETA")
		var dieta3 = Dieta("Dieta por puntos",2000.0, "Esta es una dieta hipercalorica", "7 semanas", "DIETA")
		var dieta4 = Dieta("Dieta4",2000.0, "Esta es una dieta hipercalorica", "7 semanas", "DIETA")

		var comment1 = Comment("Comentario 2021", "Este es el cuerpo del comentario" , "COMMENT")

		userService.save(usuario1)
		trainerService.save(trainer1)
		trainerService.save(trainer2)
		trainerService.save(trainer3)
		trainerService.save(trainer4)
		dietaService.save(dieta1)
		dietaService.save(dieta2)
		dietaService.save(dieta3)
		dietaService.save(dieta4)
		commentService.save(comment1)
	}

}

fun main(args: Array<String>) {
	runApplication<TraineraceApplication>(*args)
}

