package deu.java002_02.study.server.service;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import deu.java002_02.study.main.Service;
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

	@Override
	public boolean tryExecuteService()
	{
		try
		{
			FileInputStream input = new FileInputStream(m_path);
			InputStreamReader isr = new InputStreamReader(input);
			BufferedReader br = new BufferedReader(isr);

			while(true)
			{
				String line = br.readLine();

				if(line == null)
					break;
				else
					m_netModule.writeLine(line);
			}

			br.close();
			isr.close();
			input.close();

			return true;
		}
		catch(FileNotFoundException _fnfEx)
		{
			return false;
		}
		catch (IOException _ioEx)
		{
			return false;
		}
		finally
		{
			m_netModule.writeLine(NetworkLiteral.EOF);
		}
	}
}