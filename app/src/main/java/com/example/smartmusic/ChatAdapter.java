package com.example.smartmusic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/** ChatAdapter.java
 * @author Suleman, Austin, Patrick
 * This java class file contains the multiple recycler Views
 * and displays the chat messages in a Text View inside the recycler view
 * in the Display Message activity.
 * date: 04-28-22
 */
public class ChatAdapter extends RecyclerView.Adapter {

    private ArrayList<Chats> chatsArrayList;
    private Context context;

    public ChatAdapter(ArrayList<Chats> chatsArrayList, Context context) {
        this.chatsArrayList = chatsArrayList;
        this.context = context;
    }

    /**
     * this method creates the recycler view for the chat messages to be displayed
     * below code is to switch our layout type along with view holder.
     */
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case 0:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_msg,parent,false);
                return new UserViewHolder(view);
            case 1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bot_msg,parent,false);
                return new BotViewHolder(view);
        }
        return null;
    }

    /**
     * this method is used to set data to our layout file.
     * this method displays the message from either the user or bot.
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Chats chats = chatsArrayList.get(position);
        switch(chats.getSender()) {
            case "user":
                ((UserViewHolder)holder).user.setText(chats.getMessage());
                break;
            case "bot":
                ((BotViewHolder)holder).bot.setText(chats.getMessage());
                break;
        }
    }

    /**
     * this method determines if it's either the user or bot
     * @return an int - '0' for "user", '1' for "bot", '-1' for "default"
     */
    @Override
    public int getItemViewType(int position) {
        switch(chatsArrayList.get(position).getSender()) {
            case "user":
                return 0;
            case "bot":
                return 1;
            default:
                return -1;
        }
    }

    /**
     * this method returns the size of the arraylist
     */
    @Override
    public int getItemCount() {
        return chatsArrayList.size();
    }

    /**
     * this method displays the messages from the user in a Text view
     */
    public static class UserViewHolder extends RecyclerView.ViewHolder {

        TextView user;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            user = itemView.findViewById(R.id.idUser);
        }
    }

    /**
     * this method displays the messages from the bot in a Text view
     */
    public static class BotViewHolder extends RecyclerView.ViewHolder {
        TextView bot;

        public BotViewHolder(@NonNull View itemView) {
            super(itemView);
            bot = itemView.findViewById(R.id.idBot);
            }
        }
}