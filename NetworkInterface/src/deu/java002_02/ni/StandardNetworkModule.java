package deu.java002_02.ni;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public final class StandardNetworkModule implements INetworkModule
{
	private Socket m_socket;
	
	private InputStreamReader m_isr;
	private BufferedReader m_br;

	private OutputStreamWriter m_osw;
	private BufferedWriter m_bw;

	public StandardNetworkModule(Socket _socket)
	{
		m_socket = _socket;

		try
		{
			m_isr = new InputStreamReader(_socket.getInputStream());
			m_br = new BufferedReader(m_isr);
			m_osw = new OutputStreamWriter(_socket.getOutputStream());
			m_bw = new BufferedWriter(m_osw);
		}
		catch (IOException e)
		{
			System.out.println("입출력 스트림 생성에 실패했습니다.");
		}
	}
	
	public final void stop()
	{
		try
		{
			m_bw.close();
			m_osw.close();
			m_br.close();
			m_isr.close();
			m_socket.close();
		}
		catch (IOException e)
		{
			
		}
	}

	@Override
	public final String readLine()
	{
		try
		{
			return m_br.readLine();
		}
		catch (IOException e)
		{
			this.stop();
			System.out.println("StandardNetworkModule.readLine() : 네트워크로부터 입력받을 수 없습니다.");
			return null;
		}
	}

	@Override
	public final boolean writeLine(String _message)
	{
		try
		{
			m_bw.write(_message + "\n");
			m_bw.flush();
			return true;
		}
		catch (IOException e)
		{
			this.stop();
			System.out.println("StandardNetworkModule.writeLine() : 네트워크에 값을 쓸 수 없습니다.");
			return false;
		}
	}

	@Override
	public int readByte()
	{
		try
		{
			return m_socket.getInputStream().read();
		}
		catch (IOException e)
		{
			this.stop();
			System.out.println("StandardNetworkModule.readByte() : 네트워크로부터 입력받을 수 없습니다.");
			return -1;
		}
	}

	@Override
	public boolean writeByte(int _byte)
	{
		try
		{
			m_socket.getOutputStream().write(_byte);
			m_socket.getOutputStream().flush();
			return true;
		}
		catch (IOException e)
		{
			this.stop();
			System.out.println("StandardNetworkModule.writeByte() : 네트워크에 값을 쓸 수 없습니다.");
			return false;
		}
	}
}
