package edu.school21.sockets.app;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(separators = "=")
public class ArgCommander {
    @Parameter(names = "--port", required = true)
    public int port;
}
