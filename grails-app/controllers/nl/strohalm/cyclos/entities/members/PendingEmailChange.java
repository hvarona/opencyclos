/*
    This file is part of Cyclos (www.cyclos.org).
    A project of the Social Trade Organisation (www.socialtrade.org).

    Cyclos is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    Cyclos is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Cyclos; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA

 */
package nl.strohalm.cyclos.entities.members;

import java.util.Calendar;
import java.util.Map;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.settings.LocalSettings;

/**
 * Tracks a pending e-mail change until it is confirmed
 *
 * @author luis
 */
@javax.persistence.Entity
@Table(name = "pending_email_changes")
public class PendingEmailChange extends Entity {

    public static enum Relationships implements Relationship {
        MEMBER("member"), BY("by");
        private final String name;

        private Relationships(final String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }

    private static final long serialVersionUID = -1968070598567991893L;

    private Calendar creationDate;
    private Member member;
    private String validationKey;
    private String newEmail;
    private Calendar lastEmailDate;
    private Element by;
    private String remoteAddress;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Override
    public Long getId() {
        return super.getId();
    }

    @ManyToOne(targetEntity = Element.class)
    @JoinColumn(name = "by_id")
    public Element getBy() {
        return by;
    }

    @Column(name = "creation_date", nullable = false)
    public Calendar getCreationDate() {
        return creationDate;
    }

    @Column(name = "last_email_date")
    public Calendar getLastEmailDate() {
        return lastEmailDate;
    }

    @ManyToOne(targetEntity = Member.class)
    @JoinColumn(name = "member_id", nullable = false)
    public Member getMember() {
        return member;
    }

    @Column(name = "new_email", nullable = false, length = 150)
    public String getNewEmail() {
        return newEmail;
    }

    @Column(name = "remote_address", nullable = false, length = 100)
    public String getRemoteAddress() {
        return remoteAddress;
    }

    @Column(name = "validation_key", length = 64)
    public String getValidationKey() {
        return validationKey;
    }

    public void setBy(final Element by) {
        this.by = by;
    }

    public void setCreationDate(final Calendar creationDate) {
        this.creationDate = creationDate;
    }

    public void setLastEmailDate(final Calendar lastEmailDate) {
        this.lastEmailDate = lastEmailDate;
    }

    public void setMember(final Member member) {
        this.member = member;
    }

    public void setNewEmail(final String newEmail) {
        this.newEmail = newEmail;
    }

    public void setRemoteAddress(final String remoteAddress) {
        this.remoteAddress = remoteAddress;
    }

    public void setValidationKey(final String validationKey) {
        this.validationKey = validationKey;
    }

    @Override
    public String toString() {
        return getId() + " - " + newEmail + " for " + member;
    }

    @Override
    protected void appendVariableValues(final Map<String, Object> variables, final LocalSettings localSettings) {
        if (member != null) {
            variables.putAll(member.getVariableValues(localSettings));
        }
        variables.put("new_email", newEmail);
    }

}
