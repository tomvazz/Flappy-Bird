import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class Motion implements KeyListener{
	
	public static JLabel floor1;
	public static JLabel floor2;
	public static ImageIcon movingfloor;
	public JLabel bird;
	public ImageIcon imgbluebirdup;
	public ImageIcon imgbluebirddown;
	public ImageIcon imgbluebird;
	public ImageIcon[] pipe = new ImageIcon[8];
	public JLabel[] pipelbl = new JLabel[8];
	public JLabel counter;
	public int score = 0;
	public int spacestop = 0;
	public int birdhit = 0;
	
	boolean firstmove = true;
	boolean spacehit = false;
	double birdpos = 350;
	double birdvel = 10;
	int birdstart = 0;
	Timer jump = new Timer();
	TimerTask jumptask = new TimerTask() {
		public void run() {
			if (birdstart == 1) {
				if (spacehit) {
					birdvel = 10;
					spacehit = false;
				}
				if (birdhit == 1) {
					birdvel = -5;
					if (birdpos >= 590) {
						birdstart = 0;
					}
				}
				birdpos = (birdpos - birdvel);
				if (birdvel >= 0) {
					bird.setIcon(imgbluebirdup);
				} else if (birdvel <= -5) {
					bird.setIcon(imgbluebirddown);
				} else {
					bird.setIcon(imgbluebird);
				}
				birdvel = birdvel - 0.5;
				bird.setBounds(100, (int)birdpos, 50, 50);
			}
		}
	};
	
	public double[] pipepos = new double[] {500, 500, 500, 500 ,500 , 500, 500, 500};
	public double[] upperboundgap = new double[] {108, 138, 166, 196, 237, 267, 297, 347};
	public double[] lowerboundgap = new double[] {253, 283, 313, 343, 383, 413, 443, 493};
	public int go = 0;
	public int randint1 = 0;
	int pipestart = 0;
	Timer pipes1 = new Timer();
	TimerTask pipemotion1 = new TimerTask() {
		public void run() {
			if (pipestart == 1) {
				pipepos[randint1] -= 2;
				pipelbl[randint1].setBounds((int)pipepos[randint1], -4, 90, 640);
				
				if (pipepos[randint1] <= 200) {
					go = 1;
				}
				if (pipepos[randint1] <= -90) {
					pipepos[randint1] = 500;
					piperandomizer1();
				}
				if (pipepos[randint1] == 10) {
					score++;
					counter.setText(String.valueOf(score));
				}
				collision(randint1);
			}
		}
	};
	
	public int randint2 = 4;
	Timer pipes2 = new Timer();
	TimerTask pipemotion2 = new TimerTask() {
		public void run() {
			if (pipestart == 1) {
				if (go == 1) {
					pipepos[randint2] -= 2;
					pipelbl[randint2].setBounds((int)pipepos[randint2], -4, 90, 640);
					if (pipepos[randint2] <= -90) {
						pipepos[randint2] = 500;
						piperandomizer2();
					}
					if (pipepos[randint2] == 10) {
						score++;
						counter.setText(String.valueOf(score));
					}
					collision(randint2);
				}
				
			}
		}
	};
	
	public double f1 = 0;
	public double f1vel = 2;
	public double f2 = 500;
	public double f2vel = 2;
	public int stop = 1;
	Timer floortimer = new Timer();
	TimerTask floortask = new TimerTask() {
		public void run() {
			if (stop == 0) {
				f1 = f1 - f1vel;
				if (f1 == -500) {
					f1 = 0;
				}
				f2 = f2 - f2vel;
				if (f2 == 0) {
					f2 = 500;
				}
				floor1.setBounds((int)f1, 623, 500, 40);
				floor2.setBounds((int)f2, 623, 500, 40);
			}
		}
	};

	public void animate(JFrame f) {
		
		imgbluebird = new ImageIcon(this.getClass().getResource("/bluebird.png"));
		imgbluebirdup = new ImageIcon(this.getClass().getResource("/bluebirdup.png"));
		imgbluebirddown = new ImageIcon(this.getClass().getResource("/bluebirddown.png"));
		movingfloor = new ImageIcon(this.getClass().getResource("/floor.png"));
		pipe[0] = new ImageIcon(this.getClass().getResource("/pipe1.png"));
		pipe[1] = new ImageIcon(this.getClass().getResource("/pipe2.png"));
		pipe[2] = new ImageIcon(this.getClass().getResource("/pipe3.png"));
		pipe[3] = new ImageIcon(this.getClass().getResource("/pipe4.png"));
		pipe[4] = new ImageIcon(this.getClass().getResource("/pipe5.png"));
		pipe[5] = new ImageIcon(this.getClass().getResource("/pipe6.png"));
		pipe[6] = new ImageIcon(this.getClass().getResource("/pipe7.png"));
		pipe[7] = new ImageIcon(this.getClass().getResource("/pipe8.png"));
		
		counter = new JLabel(String.valueOf(score));
		counter.setFont(new Font("Apple LiGothic", Font.PLAIN, 90));
		counter.setForeground(Color.WHITE);
		counter.setHorizontalAlignment(SwingConstants.CENTER);
		counter.setBounds(200, 110, 100, 100);
		f.getContentPane().add(counter);
		
		bird = new JLabel(imgbluebird);
		bird.setBounds(100, (int)birdpos, 50, 50);
		f.getContentPane().add(bird);
		
		floor1 = new JLabel(movingfloor);
		floor1.setBounds((int)f1, 623, 500, 40);
		f.getContentPane().add(floor1);
		floor2 = new JLabel(movingfloor);
		floor2.setBounds((int)f2, 623, 500, 40);
		f.getContentPane().add(floor2);

		//timers start
		floortimer.schedule(floortask, 0, 10);
		
		for (int a = 0; a < 8; a++) {
			pipelbl[a] = new JLabel(pipe[a]);
			pipelbl[a].setBounds((int)pipepos[a], -4, 90, 640);
			f.getContentPane().add(pipelbl[a]);
		}
		
		f.addKeyListener(this);
		f.setFocusable(true);
		
		//timers start
		jump.schedule(jumptask, 0, 12);
		pipes1.schedule(pipemotion1, 1000, 10);
		pipes2.schedule(pipemotion2, 1000, 10);
		
		
		
	}
	
	public void keyTyped(KeyEvent e) {}
	public void keyPressed(KeyEvent e) {
		int spacecode = e.getKeyCode();
		if (spacecode == KeyEvent.VK_SPACE && spacestop == 0) {
			if (pipestart == 0 && birdstart == 0) {
				birdpos = 350;
				bird.setBounds(100, (int)birdpos, 50, 50);
				firstmove = true;
			}
			if (firstmove) {
				pipestart = 1;
				birdstart = 1;
				stop = 0;
				firstmove = false;
			} else {
				spacehit = true;
			}
		}
		int returncode = e.getKeyCode();
		if (returncode == KeyEvent.VK_ENTER) {
			stop = 0;
			for (int a = 0; a < 8; a++) {
				pipepos[a] = 500;
				pipelbl[a].setBounds((int)pipepos[a], -4, 90, 640);
			}
			birdpos = 350;
			bird.setIcon(imgbluebird);
			bird.setBounds(100, (int)birdpos, 50, 50);
			firstmove = true;
			go = 0;
			birdvel = 10;
			birdstart = 0;
			pipestart = 0;
			birdhit = 0;
			spacestop = 0;
			score = 0;
			randint1 = 0;
			randint2 = 4;
			counter.setText(String.valueOf(score));

		}
		
	}
	public void keyReleased(KeyEvent e) {}
	
	
	public void piperandomizer1(){
		int randnum1 = (int)(Math.random() * 4 + 0);
		randint1 = randnum1;
	}
	public void piperandomizer2(){
		int randnum2 = (int)(Math.random() * 4 + 4);
		randint2 = randnum2;
	}
	public void collision(int index) {
		if ((pipepos[index] > 12 && pipepos[index] < 147) && (birdpos < upperboundgap[index] || birdpos > lowerboundgap[index])) {
			spacestop = 1;
			pipestart = 0;
			birdhit = 1;
			stop = 1;
		}
		if (birdpos >= 600) {
			pipestart = 0;
			birdstart = 0;
		}
	}
}
