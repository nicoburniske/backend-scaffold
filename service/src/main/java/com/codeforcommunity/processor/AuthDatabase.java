package com.codeforcommunity.processor;

import com.codeforcommunity.auth.AuthUtils;
import com.codeforcommunity.exceptions.CreateUserException;
import org.jooq.DSLContext;
import org.jooq.generated.Tables;
import org.jooq.generated.tables.pojos.NoteUser;
import org.jooq.generated.tables.records.NoteUserRecord;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

import static org.jooq.generated.Tables.NOTE_USER;

public class AuthDatabase {

    private final DSLContext db;
    private AuthUtils sha;

    public AuthDatabase(DSLContext db) {
        this.sha = new AuthUtils();
        this.db = db;
    }

    public boolean isValidLogin(String user, String pass) {
        Optional<NoteUser> maybeUser = Optional.ofNullable(db
            .selectFrom(NOTE_USER)
            .where(NOTE_USER.USER_NAME.eq(user))
            .fetchOneInto(NoteUser.class));

        return maybeUser
            .filter(noteUser -> sha.hash(pass).equals(noteUser.getPassHash()))
            .isPresent();
    }

    public void createNewUser(String username, String email, String password, String firstName, String lastName) {

        boolean emailUsed = db.fetchExists(db.selectFrom(NOTE_USER).where(NOTE_USER.EMAIL.eq(email)));
        boolean usernameUsed = db.fetchExists(db.selectFrom(NOTE_USER).where(NOTE_USER.USER_NAME.eq(username)));
        if (emailUsed || usernameUsed) {
            if (emailUsed && usernameUsed) {
                throw new CreateUserException(CreateUserException.UsedField.BOTH);
            } else if (emailUsed) {
                throw new CreateUserException(CreateUserException.UsedField.EMAIL);
            } else {
                throw new CreateUserException(CreateUserException.UsedField.USERNAME);
            }
        }

        String pass_hash = sha.hash(password);
        NoteUserRecord newUser = db.newRecord(NOTE_USER);
        newUser.setUserName(username);
        newUser.setEmail(email);
        newUser.setPassHash(pass_hash);
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.store();
    }

    public void addToBlackList(String signature) {
        Timestamp expirationTimestamp = Timestamp.from(Instant.now().plusMillis(AuthUtils.refresh_exp));
        db.newRecord(Tables.BLACKLISTED_REFRESHES)
            .values(signature, expirationTimestamp)
            .store();
    }

    public boolean isOnBlackList(String signature) {
        int count = db.fetchCount(
            Tables.BLACKLISTED_REFRESHES
                .where(Tables.BLACKLISTED_REFRESHES.REFRESH_HASH.eq(signature)));

        return count >= 1;
    }
}
