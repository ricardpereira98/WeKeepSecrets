import java.util.Scanner;
import kSecrets.*;
import kSecrets.Accesses.*;
import kSecrets.Documents.*;
import kSecrets.Iterators.*;
import kSecrets.Users.*;

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
	private static final String TOP_GRANTED = "TOPGRANTED";
	private static final String USER_DOCS = "USERDOCS";
	private static final String REVOKE = "REVOKE";
	private static final String GRANT = "GRANT";
	private static final String READ = "READ";
	private static final String WRITE = "WRITE";

	// Constantes que definem as mensagens para o utilizador

	public static final String UNKNOWN_COMMAND = "Unknown command. Type help to see available commands.";
	public static final String USER_REGISTERED = "User %s was registered.\n";
	public static final String USER_INVALID = "Identifier %s is already assigned to another user.\n";
	public static final String NOT_REGISTERED = "Not a registered user.\n";
	public static final String NO_USERS = "There are no registered users.";
	public static final String NO_DOCUMENT = "Document %s does not exist in the user account.\n";
	public static final String DOC_EXISTS = "Document %s already exists in the user account.\n";
	public static final String INSUFFICIENT_CLEARANCE = "Insufficient security clearance.";
	public static final String CANNOT_UPDATE = "Document %s cannot be updated.\n";
	public static final String DOC_UPLOADED = "Document %s was uploaded.\n";
	public static final String DOC_UPDATED = "Document %s was updated.\n";
	public static final String CLERK_ERROR = "Grants can only be issued between officers.\n";
	public static final String ALREADY_ACCESS = "Already has access to document %s.\n";
	public static final String GRANT_DOESNT_EXIST = "Grant for officer %s does not exist.\n";
	public static final String GRANT_REVOKED = "Grant for officer %s was already revoked.\n";
	public static final String GRANT_GRANTED = "Access to document %s has been granted.\n";
	public static final String GRANT_BEEN_REVOKED = "Access to document %s has been revoked.\n";
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

			case TOP_GRANTED:
				listGrantedUserss(kSecrets);
				break;

			default:
				System.out.println(UNKNOWN_COMMAND);
			}

			comm = getCommand(in);
		}
		System.out.println(QUIT_MSG);
		in.close();
	}

	private static String getCommand(Scanner in) {
		String input;
		// System.out.print("> ");
		input = in.nextLine().toUpperCase();
		return input;
	}

	private static void printHelp() {
		System.out.println("register - registers a new user");
		System.out.println("listusers - list all registered users");
		System.out.println("upload - upload a document");
		System.out.println("read - read a document");
		System.out.println("write - write a document");
		System.out.println("grant - grant access to a document");
		System.out.println("revoke - revoke a grant to access a document");
		System.out.println("userdocs - list the official or classified documents of an user");
		System.out.println("topleaked - list the top 10 documents with more grants");
		System.out.println("topgranters - list the top 10 officers that have given more grants");
		System.out.println("help - shows the available commands");
		System.out.println("exit - terminates the execution of the program");
	}

	private static void regist(Scanner in, KeepingSecrets kSecrets) {
		String kind, userID, clearanceLevel;

		kind = in.next().trim();
		userID = in.next().trim();
		clearanceLevel = in.nextLine().trim();

		if (kSecrets.hasUserID(userID)) {

			System.out.printf(USER_INVALID, userID);

		} else {

			if (kSecrets.isClearanceOfficial(clearanceLevel)) {

				kSecrets.addClerk(kind, userID, clearanceLevel);

			} else {

				kSecrets.addOfficer(kind, userID, clearanceLevel);
			}

			System.out.printf(USER_REGISTERED, userID);
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
				System.out.println(user.getKind() + " " + user.getID() + " " + user.getClearanceLevel());
			}
		}
	}

	private static void upload(Scanner in, KeepingSecrets kSecrets) {
		String documentName, userID, secLvl, description;

		documentName = in.next().trim();
		userID = in.next().trim();
		secLvl = in.nextLine().trim(); // official, confidential, secret, topsecret
		description = in.nextLine().trim();

		if (kSecrets.hasUserID(userID)) {
			if (!kSecrets.hasDocumentUploaded(userID, documentName)) {
				if (kSecrets.isClearanceHighEnough(userID, secLvl)) {
					kSecrets.uploadDoc(documentName, userID, secLvl, description);
					System.out.printf(DOC_UPLOADED, documentName);
				} else {
					System.out.println(INSUFFICIENT_CLEARANCE);
				}
			} else {
				System.out.printf(DOC_EXISTS, documentName);
			}
		} else {
			System.out.printf(NOT_REGISTERED);
		}

	}

	private static void write(Scanner in, KeepingSecrets kSecrets) {
		String documentName, managerID, updaterID, newDescription;

		documentName = in.next().trim();
		managerID = in.next().trim();
		updaterID = in.nextLine().trim();
		newDescription = in.nextLine().trim();

		if (kSecrets.hasUserID(managerID) && kSecrets.hasUserID(updaterID)) {
			if (kSecrets.hasDocumentUploaded(managerID, documentName)) {
				if (!kSecrets.isDocOfficial(documentName, managerID)) {
					String docSecLvl = kSecrets.getDocSecurityLevel(documentName, managerID);
					if (kSecrets.isClearanceHighEnough(updaterID, docSecLvl)) {
						kSecrets.updateDescription(documentName, managerID, newDescription);
						System.out.printf(DOC_UPDATED, documentName);
					} else {
						System.out.println(INSUFFICIENT_CLEARANCE);
					}
				} else {
					System.out.printf(CANNOT_UPDATE, documentName);
				}
			} else {
				System.out.printf(NO_DOCUMENT, documentName);
			}
		} else {
			System.out.printf(NOT_REGISTERED);
		}

	}

	private static void read(Scanner in, KeepingSecrets kSecrets) {
		String documentName, managerID, readerID;

		documentName = in.next().trim();
		managerID = in.next().trim();
		readerID = in.nextLine().trim();

		if (!kSecrets.hasUserID(managerID) || !kSecrets.hasUserID(readerID)) {
			System.out.printf(NOT_REGISTERED);
		}

		else if (!kSecrets.hasDocumentUploaded(managerID, documentName)) {
			System.out.printf(NO_DOCUMENT, documentName);
		}

		else if (!kSecrets.isClearanceHighEnough(readerID, kSecrets.getDocSecurityLevel(documentName, managerID))
				&& !kSecrets.hasAccess(documentName, managerID, readerID)) {
			System.out.println(INSUFFICIENT_CLEARANCE);
		}

		else {
			System.out.println("Document: " + kSecrets.getDescription(documentName, managerID));
		}

	}

	private static void grant(Scanner in, KeepingSecrets kSecrets) {
		String documentName, managerID, grantedID;

		documentName = in.next().trim();
		managerID = in.next().trim();
		grantedID = in.nextLine().trim();

		if (!kSecrets.hasUserID(managerID) || !kSecrets.hasUserID(grantedID)) {
			System.out.printf(NOT_REGISTERED);
		}

		else if ((kSecrets.isClerkUser(grantedID)) || kSecrets.isClerkUser(managerID)) {
			System.out.printf(CLERK_ERROR);
		}

		else if (!kSecrets.hasDocumentUploaded(managerID, documentName)) {
			System.out.printf(NO_DOCUMENT, documentName);
		}

		else if (kSecrets.isClearanceHighEnough(grantedID, kSecrets.getDocSecurityLevel(documentName, managerID))
				|| kSecrets.hasAccess(documentName, managerID, grantedID)) {
			System.out.printf(ALREADY_ACCESS, documentName);
		}

		else {
			kSecrets.grant(documentName, managerID, grantedID);
			System.out.printf(GRANT_GRANTED, documentName);
		}
	}

	private static void revoke(Scanner in, KeepingSecrets kSecrets) {
		String documentName, managerID, grantedID;

		documentName = in.next().trim();
		managerID = in.next().trim();
		grantedID = in.nextLine().trim();

		if (!kSecrets.hasUserID(managerID) || !kSecrets.hasUserID(grantedID)) {
			System.out.printf(NOT_REGISTERED);
		}

		else if (kSecrets.isClerkUser(grantedID)) {
			System.out.printf(CLERK_ERROR);
		}

		else if (!kSecrets.hasDocumentUploaded(managerID, documentName)) {
			System.out.printf(NO_DOCUMENT, documentName);
		}

		else if (!kSecrets.hasAccess(documentName, managerID, grantedID) && !kSecrets.isRevoked(documentName, managerID, grantedID)) {
			System.out.printf(GRANT_DOESNT_EXIST, grantedID);
		}

		else if (kSecrets.isRevoked(documentName, managerID, grantedID)) {
			System.out.printf(GRANT_REVOKED, grantedID);
		}

		else {
			kSecrets.revoke(documentName, managerID, grantedID);
			System.out.printf(GRANT_BEEN_REVOKED, documentName);
		}
	}

	private static void listUserDocs(Scanner in, KeepingSecrets kSecrets) {
		String userId, docType;

		userId = in.next().trim();
		docType = in.nextLine().trim();

		if (!kSecrets.hasUserID(userId)) {
			System.out.printf(NOT_REGISTERED);
		}

		/**
		 * if (only read){ print( doc name: user id, security level) }
		 * 
		 * else { line 1: doc name, security level, number of accesses line 2: user id,
		 * security level, type of access (read or write), more recent accesses shown
		 * last. line 3: grants given and revoked - user id, security level, more recent
		 * actions shown last. }
		 */

	}

	private static void listLeakedDocs(KeepingSecrets kSecrets) {

		/**
		 * - percorrer todas os docs - percorrer todos os acessos
		 * 
		 * ordenar vetor
		 * 
		 * int i = 0 (while has next && i<10 && grantpositive(doc)) { print; i++ }
		 * 
		 * boolean grantpositive(){ return grantedTimes()>0; }
		 */

	}

	private static void listGrantedUserss(KeepingSecrets kSecrets) {

		/**
		 * 
		 * not sure
		 * 
		 * - percorrer todas os users - get granted times
		 * 
		 * ordenar vetor
		 * 
		 * int i = 0 (while has next && i<10 && grantpositive(doc)) { print; i++ }
		 * 
		 * boolean grantpositive(){ return grantedTimes()>0; }
		 */

	}

}