import java.util.Scanner;
import kSecrets.*;

/**
 * 
 * @author nuno costa 54620
 * @author ricardo pereira
 *
 */

public class Main {

	// Constantes que definem os comandos
	private static final String EXIT = "EXIT";
	private static final String HELP = "HELP";
	private static final String REGISTER = "REGISTER";
	private static final String LIST_USERS = "LISTUSERS";
	private static final String UPLOAD = "UPLOAD";
	private static final String TOP_LEAKED = "TOPLEAKED";
	private static final String USER_DOCS = "USERDOCS";
	private static final String REVOKE = "REVOKE";
	private static final String GRANT = "GRANT";
	private static final String READ = "READ";
	private static final String WRITE = "WRITE";

	// Constantes que definem as mensagens para o utilizador
	public static final String UNKNOWN_COMMAND = "Unknown command. Type help to see available commands.";
	public static final String USER_REGISTERED = "User %s was registered.";
	public static final String USER_INVALID = "Identifier %s is already assigned to another user.";
	public static final String NO_USERS = "There are no registered users.";
	public static final String QUIT_MSG = "Bye!";

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		KeepingSecrets kSecrets = new KeepingSecretsClass();
		String comm = getCommand(in);
		while (!comm.equals(EXIT)) {
			switch (comm) {

			case HELP:
				printHelp();
				break;

			case REGISTER:
				regist(in, kSecrets);
				break;

			case LIST_USERS:
				listusers(kSecrets);
				break;

			case UPLOAD:
				upload(in, kSecrets);
				break;

			case WRITE:
				write(in, kSecrets);
				break;

			case READ:
				read(in, kSecrets);
				break;

			case GRANT:
				grant(in, kSecrets);
				break;

			case REVOKE:
				revoke(in, kSecrets);
				break;

			case USER_DOCS:
				listUserDocs(in, kSecrets);
				break;

			case TOP_LEAKED:
				listLeakedDocs(kSecrets);
				break;

			default:
				System.out.println(UNKNOWN_COMMAND);
			}

			System.out.println();
			comm = getCommand(in);
		}
		System.out.println(QUIT_MSG);
		System.out.println();
		in.close();
	}

	private static String getCommand(Scanner in) {
		String input;
		System.out.print("> ");
		input = in.nextLine().toUpperCase();
		return input;
	}

	private static void printHelp() {
		System.out.println("register - registers a new user");
		System.out.println("listusers - list all registered users");
		System.out.println("upload - upload a document");
		System.out.println("read - read a document");
		System.out.println("write - write a document");
		System.out.println("grant - grant access a document");
		System.out.println("revoke - revoke a grant to access a document");
		System.out.println("userdocs - list the official or classified documents of an user");
		System.out.println("topleaked - list the top 10 documents with more grants");
		System.out.println("topgranters - list the top 10 officers that have given more grants");
		System.out.println("help - shows the available commands");
		System.out.println("exit - terminates the execution of the program");
	}

	private static void regist(Scanner in, KeepingSecrets kSecrets) {
		String kind, userId, clearanceLevel;

		kind = in.next().trim();
		userId = in.next().trim();
		clearanceLevel = in.nextLine().trim();

		if (kSecrets.hasId(userId)) {
			System.out.printf(USER_INVALID, userId);
		}

		else {
			if (clearanceLevel.equals("OFFICIAL")) {

				kSecrets.addClerk(kind, userId, clearanceLevel);
				System.out.printf(USER_REGISTERED, userId);
			} else {

				kSecrets.addOfficer(kind, userId, clearanceLevel);
				System.out.printf(USER_REGISTERED, userId);
			}
		}

	}

	private static void listusers(KeepingSecrets kSecrets) {

		if (kSecrets.isEmpty()) {
			System.out.println(NO_USERS);
		}

		else {
			Iterator it = kSecrets.listUsers();
			while (it.hasNext()) {
				User user = it.next();
				System.out.println(user.getKind() + " " + user.getId() + " " + user.getClearanceLevel());
			}
		}
	}

	private static void upload(Scanner in, KeepingSecrets kSecrets) {
		String documentName, userId, securityLevel, description;

		documentName = in.next();
		userId = in.next();
		securityLevel = in.nextLine();
		description = in.nextLine();
	}

	private static void write(Scanner in, KeepingSecrets kSecrets) {

	}

	private static void read(Scanner in, KeepingSecrets kSecrets) {

	}

	private static void grant(Scanner in, KeepingSecrets kSecrets) {

	}

	private static void revoke(Scanner in, KeepingSecrets kSecrets) {

	}

	private static void listUserDocs(Scanner in, KeepingSecrets kSecrets) {

	}

	private static void listLeakedDocs(KeepingSecrets kSecrets) {

	}

}