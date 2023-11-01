package deu.java002_02.study.server.service;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import deu.java002_02.study.ni.INetworkModule;
import deu.java002_02.study.ni.INetworkService;
import deu.java002_02.study.ni.NetworkLiteral;

public final class ReadFileService extends Service implements INetworkService
{
	private String m_path;
	private INetworkModule m_netModule;

	public ReadFileService(String _path)
	{
		m_path = _path;
	}

	public void bindNetworkModule(INetworkModule _netModule)
	{
		m_netModule = _netModule;
	}

	public void onService()
	{
		if(m_netModule == null)
			return;

		try
		{
			FileInputStream input = new FileInputStream(m_path);
			InputStreamReader isr = new InputStreamReader(input);
			BufferedReader br = new BufferedReader(isr);

			try
			{
				while(true)
				{
					String line = br.readLine();

					if(line == null)
					{
						m_netModule.writeLine(NetworkLiteral.EOF);
						break;
					}
					else
					{
						m_netModule.writeLine(line);
					}
				}
			}
			catch (IOException e)
			{
				m_netModule.writeLine(NetworkLiteral.EOF);
	
				// NOTE: End of File
				try
				{
					br.close();
					isr.close();
					input.close();
				}
				catch (IOException e1)
				{
					
				}
			}
		}
		catch (FileNotFoundException e)
		{
			m_netModule.writeLine(NetworkLiteral.EOF);
			System.out.println(String.format("파일을 찾을 수 없습니다."));
		}
	}
}