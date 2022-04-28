package com.example.smartmusic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/** ChatRVAdapter.java
 * @author Suleman, Austin, Patrick
 * This java class file contains the RecyclerView adapter and displays the chat messages in the
 * activity.
 * date: 04-28-22
 */
public class ChatRVAdapter extends RecyclerView.Adapter {

    private ArrayList<ChatsModal> chatsModalArrayList;
    private Context context;

    public ChatRVAdapter(ArrayList<ChatsModal> chatsModalArrayList, Context context) {
        this.chatsModalArrayList = chatsModalArrayList;
        this.context = context;
    }

    @Override
    /**
     * this method creates the recycler view for the messages to be displayed
     */
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case 0:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_msg_rv_item,parent,false);
                return new UserViewHolder(view);
            case 1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bot_msg_rv_item,parent,false);
                return new BotViewHolder(view);
        }
        return null;
    }

    @Override
    /**
     * this method displays the message from either the user or the chatbot.
     */
    public void onBindViewHolder( RecyclerView.ViewHolder holder, int position) {
        ChatsModal chatsModal = chatsModalArrayList.get(position);
        switch(chatsModal.getSender()) {
            case "user":
                ((UserViewHolder)holder).user.setText(chatsModal.getMessage());
                break;
            case "bot":
                ((BotViewHolder)holder).bot.setText(chatsModal.getMessage());
                break;
        }
    }

    @Override
    /**
     * this method determines if it's either the user or bot
     * @return an int
     */
    public int getItemViewType(int position) {
        switch(chatsModalArrayList.get(position).getSender()) {
            case "user":
                return 0;
            case "bot":
                return 1;
            default:
                return -1;
        }
    }

    @Override
    /**
     * this method returns the size of the messages.
     */
    public int getItemCount() {
        return chatsModalArrayList.size();
    }

    /**
     * this method displays the messages from the user in a text view
     */
    public static class UserViewHolder extends RecyclerView.ViewHolder {

        TextView user;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            user = itemView.findViewById(R.id.idTVUser);
        }
    }
    /**
     * this method displays the messages from the bot in a text view
     */
    public static class BotViewHolder extends RecyclerView.ViewHolder {
        TextView bot;

        public BotViewHolder(View itemView) {
            super(itemView);
            bot = itemView.findViewById(R.id.idTVBot);
            }
        }
}
