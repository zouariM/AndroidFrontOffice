package runingtracking;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import runingtracking.domain.Position;
import runingtracking.domain.RunningTrack;
import runingtracking.domain.Runway;
import runingtracking.domain.User;
import runingtracking.repository.RunningTrackRepository;

//@Component
public class InitDataBase implements CommandLineRunner {
	
	private Logger log = LoggerFactory.getLogger(RunningTrackingApplication.class);
	@Autowired
	RunningTrackRepository userRepository;

	@Override
	public void run(String... args) throws Exception {
		log.info("run call ------------");
		//Reinit data base
		userRepository.deleteAll();
		
		
		/*RunningTrack u1 = new RunningTrack(new User(args[0], args[1],"xx"));
		Runway run1 = new Runway();
		run1.setStartTime(System.currentTimeMillis());
		run1.addPosition(new Position(0d, 1d));
		run1.addPosition(new Position(2d, 3d));
		run1.setFinishTime(System.currentTimeMillis());
		u1.addRunway(run1);
		
		Runway run2 = new Runway();
		run1.setStartTime(System.currentTimeMillis());
		run2.addPosition(new Position(4d, 5d));
		run2.addPosition(new Position(6d, 7d));
		run1.setFinishTime(System.currentTimeMillis());
		u1.addRunway(run2);*/
		
		//Save a couple of users
		//userRepository.save(u1);
		
		//fetch all Users
		/*System.out.println("Users found with findAll()");
		System.out.println("--------------------------");
		userRepository.findAll().forEach(System.out::println);
		*/
		
	}

}
