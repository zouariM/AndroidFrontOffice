package runingtracking;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import runingtracking.domain.Position;
import runingtracking.domain.RunningTrack;
import runingtracking.domain.User;
import runingtracking.repository.RunningTrackRepository;

@Component
public class InitDataBase implements CommandLineRunner {
	
	private Logger log = LoggerFactory.getLogger(RunningTrackingApplication.class);
	@Autowired
	RunningTrackRepository userRepository;

	@Override
	public void run(String... args) throws Exception {
		log.info("run call ------------");
		//Reinit data base
		userRepository.deleteAll();
		
		
		RunningTrack u1 = new RunningTrack(new User(args[0], args[1],"xx"));
		u1.addPosition(new Position(0d, 1d));
		u1.addPosition(new Position(2d, 3d));
		
		RunningTrack u2 = new RunningTrack(new User(args[2], args[3], "xx"));
		u2.addPosition(new Position(4d, 5d));
		u2.addPosition(new Position(6d, 7d));
		
		//Save a couple of users
		userRepository.save(u1);
		userRepository.save(u2);
		
		//fetch all Users
		System.out.println("Users found with findAll()");
		System.out.println("--------------------------");
		userRepository.findAll().forEach(System.out::println);
		
		
	}

}
