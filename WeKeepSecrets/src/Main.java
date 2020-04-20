import java.util.Scanner;
import kSecrets.*;
import kSecrets.Accesses.*;
import kSecrets.Documents.*;
import kSecrets.Iterators.*;
import kSecrets.Users.*;

/**
 * 
 * @author Nuno Costa 54620 && Jose Pereira 55204
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
	private static final String TOP_GRANTED = "TOPGRANTERS";
	private static final String USER_DOCS = "USERDOCS";
	private static final String REVOKE = "REVOKE";
	private static final String GRANT = "GRANT";
	private static final String READ = "READ";
	private static final String WRITE = "WRITE";
	private static final String OFFICIAL = "OFFICIAL";

	// Constantes que definem as mensagens para o utilizador

	private static final String NO_LEAKED_DOCS = "There are no leaked documents.";
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
	public static final String NO_GRANTS_GIVEN = "No officer has given grants.";
	public static final String INAPPROPRIATE_SLVL = "Inappropriate security level.";
	public static final String NO_OFFICIAL_DOCS = "There are no official documents.";
	public static final String NO_CLASSIFIED_DOCS = "There are no classified documents.";
	public static final String NO_ACCESSES = "There are no accesses.";
	public static final String NO_GRANTS = "There are no grants.";
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
				listGrantedUsers(kSecrets);
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

			kSecrets.addUser(kind, userID, clearanceLevel);
			System.out.printf(USER_REGISTERED, userID);
		}
	}

	private static void listusers(KeepingSecrets kSecrets) {

		if (kSecrets.isEmpty()) {
			System.out.println(NO_USERS);
		}

		else {
			UserIterator it = kSecrets.usersIterator();
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

					if (secLvl.toUpperCase().equals(OFFICIAL)) {
						// if(kSecrets.isDocOfficial(documentName, userID)) { //erro
						kSecrets.uploadOfficialDoc(documentName, userID, secLvl, description);
					}

					else {
						kSecrets.uploadClassifiedDoc(documentName, userID, secLvl, description);
					}

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

						kSecrets.updateDescription(documentName, managerID, newDescription, updaterID);
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
			System.out.println("Document: " + kSecrets.getDescription(documentName, managerID, readerID));
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

		else if (!kSecrets.hasAccess(documentName, managerID, grantedID)
				&& !kSecrets.isRevoked(documentName, managerID, grantedID)) {
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
		String userID, docType;

		userID = in.next().trim();
		docType = in.nextLine().trim();

		if (!kSecrets.hasUserID(userID)) {
			System.out.printf(NOT_REGISTERED);
		}

		else {

			if (docType.toUpperCase().equals(OFFICIAL)) {

				if (!kSecrets.userHasOfficialDocs(userID)) {
					System.out.println(NO_OFFICIAL_DOCS);
				}

				else {
					// lista os documentos official
					DocumentIterator it = kSecrets.listOfficialDocsIterator(userID);

					while (it.hasNext()) {
						Document doc = it.next();

						if (doc.getNumAccesses() == 0) {
							System.out.printf("%s %d: %s\n", doc.getDocName(), doc.getNumAccesses(), NO_ACCESSES);
						}

						else {
							System.out.printf("%s %d: %s %s\n", doc.getDocName(), doc.getNumAccesses(),
									doc.getManager(), doc.getSecurityLevel());
						}
					}
				}
			}

			else {

				if (!kSecrets.isClearanceAppropriated(userID)) {
					System.out.println(INAPPROPRIATE_SLVL);
				}

				else {

					if (!kSecrets.userHasClassifiedDocs(userID)) {
						System.out.println(NO_CLASSIFIED_DOCS);
					}

					else {

						// lista os documentos classified
						DocumentIterator it = kSecrets.listClassifiedDocsIterator(userID);

						while (it.hasNext()) {
							Document doc = it.next();
							System.out.printf("%s %s %s\n", doc.getDocName(), doc.getSecurityLevel(),
									doc.getNumAccesses());

							if (doc.getNumAccesses() == 0) {
								System.out.println(NO_ACCESSES);
							}

							else {
								for (int i = 0; i < doc.getTotalNumAccesses(); i++) {
									Accesses reader = doc.getAccess(i);
									if (kSecrets.isReadWriteAccess(reader)) {
										System.out.printf("%s [%s, %s]", reader.getReaderID(),
												reader.getReaderClearanceLvl(), reader.getAccessType());
										if (i < doc.getTotalNumAccesses() - 1) {
											System.out.print(", ");
										}
									}
								}
								System.out.printf("\n");

							}

							if (doc.getActualAccesses() == 0) {
								System.out.println(NO_GRANTS);
							}

							else {
								for (int i = 0; i < doc.getTotalNumAccesses(); i++) {
									Accesses reader = doc.getAccess(i);
									if (!kSecrets.isReadWriteAccess(reader)) {
										System.out.printf("%s [%s, %s]", reader.getReaderID(),
												reader.getReaderClearanceLvl(), reader.getAccessType());
										if (i < doc.getTotalNumAccesses() - 1) {
											System.out.print(", ");
										}
									}
								}
								System.out.printf("\n");

							}
						}
					}
				}
			}

		}

	}

	private static void listLeakedDocs(KeepingSecrets kSecrets) {

		if (kSecrets.isGrantedDocsEmpty()) {
			System.out.println(NO_LEAKED_DOCS);
		}

		else {
			DocumentIterator it = kSecrets.leakedDocsIterator();
			while (it.hasNext()) {
				Document doc = it.next();
				System.out.println(doc.getDocName() + " " + doc.getManager() + " " + doc.getSecurityLevel() + " "
						+ doc.getNumAccesses() + " " + doc.grantedTimes() + " " + doc.revokedTimes());
			}
		}
	}

	private static void listGrantedUsers(KeepingSecrets kSecrets) {

		if (kSecrets.isNumGrantersEmpty()) {
			System.out.println(NO_GRANTS_GIVEN);
		}

		else {

			UserIterator it = kSecrets.topGrantersIterator();
			while (it.hasNext()) {
				User user = it.next();
				System.out.println(user.getID() + " " + user.getClearanceLevel() + " " + user.getDocsNum() + " "
						+ user.getGrantsGiven() + " " + user.getRevokesGiven());
			}
		}

	}

}