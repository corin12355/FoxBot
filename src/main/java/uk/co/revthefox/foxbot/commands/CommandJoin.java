/*
 * This file is part of Foxbot.
 *
 *     Foxbot is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Foxbot is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Foxbot. If not, see <http://www.gnu.org/licenses/>.
 */

package uk.co.revthefox.foxbot.commands;

import org.pircbotx.User;
import org.pircbotx.hooks.events.MessageEvent;
import uk.co.revthefox.foxbot.FoxBot;

public class CommandJoin extends Command
{
    private final FoxBot foxbot;

    public CommandJoin(FoxBot foxbot)
    {
        super("join", "command.join");
        this.foxbot = foxbot;
    }

    @Override
    public void execute(final MessageEvent event, final String[] args)
    {
        User sender = event.getUser();

        if (args.length != 0)
        {
            for (String chan : args)
            {
                if (chan.startsWith("#"))
                {
                    if (!foxbot.getChannel(chan).isInviteOnly())
                    {
                        foxbot.joinChannel(chan);
                        foxbot.sendNotice(sender, String.format("Joined %s", chan));
                        continue;
                    }
                    foxbot.sendNotice(sender, String.format("%s is invite only!", chan));
                    continue;
                }
                foxbot.sendNotice(sender, String.format("%s is not a channel...", chan));
            }
            return;
        }
        foxbot.sendNotice(sender, String.format("Wrong number of args! Use %sjoin <#channel> [#channel2 #channel3 ... ]", foxbot.getConfig().getCommandPrefix()));
    }
}