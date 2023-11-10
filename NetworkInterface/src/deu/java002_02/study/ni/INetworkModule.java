package deu.java002_02.study.ni;

public interface INetworkModule
{
	boolean isClosed();

	String readLine();
	int readByte();
	boolean writeLine(String _line);
	boolean writeByte(int _byte);
}
