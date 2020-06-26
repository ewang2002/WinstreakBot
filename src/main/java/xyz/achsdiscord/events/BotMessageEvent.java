package xyz.achsdiscord.events;

import net.dv8tion.jda.api.entities.Message.Attachment;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import xyz.achsdiscord.parse.NameProcessor;
import xyz.achsdiscord.util.Utility;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public class BotMessageEvent extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) {
            return;
        }

        final List<Attachment> attachments = event.getMessage().getAttachments();
        for (Attachment attachment : attachments) {
            if (attachment.isImage()) {
                try {
                    NameProcessor processor = new NameProcessor(new URL(attachment.getUrl()))
                            .fixImage()
                            .cropImage();
                    BufferedImage image = processor.getImage();
                    processor.getPlayerNames(0, 8);
                    Utility.sendImage(event.getChannel(), image).queue();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
