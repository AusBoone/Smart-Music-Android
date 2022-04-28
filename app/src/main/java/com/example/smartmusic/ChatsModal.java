package com.example.smartmusic;

/**
 * ChatsModal.java
 * @author Suleman, Austin, Patrick
 * This java class file contains the getter, setter methods and the constructor for the sender and
 * message
 * date: 04/28/22
 */
public class ChatsModal {

        /** string to store our message and sender */
        private String message;
        private String sender;

        /** getter and setter methods. */
        public String getMessage() {
            return message;
        }

    /**
     * this method is a setter for the message
     * @param message
     */
        public void setMessage(String message) {
            this.message = message;
        }

    /**
     * this is a getter method for the sender
     * @return sender
     */
        public String getSender() {
            return sender;
        }

    /**
     * this is a setter method for the sender
     * @param sender
     */
        public void setSender(String sender) {
            this.sender = sender;
        }


    /**
     * this is a constructor for the message
     * @param message
     * @param sender
     */
        public ChatsModal(String message, String sender) {
            this.message = message;
            this.sender = sender;
        }

}