public class Listener extends Thread {
	private boolean exit;
	private GUI gui;
	private Listenable listen;

	Listener(Listenable listen, GUI gui) {
		this.gui = gui;
		this.listen = listen;
	}

	public void run() {
		exit = false;

		while (!exit) {
			try {
				sleep(1000);
				gui.setOutput(listen.listen());
			} catch (Exception e) {}
		}
	}

	public void exit() {
		exit = true;
	}
}

