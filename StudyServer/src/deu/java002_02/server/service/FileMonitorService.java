package deu.java002_02.server.service;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;

import deu.java002_02.ni.INetworkModule;
import deu.java002_02.ni.INetworkService;

public class FileMonitorService implements INetworkService
{
	@Override
	public void onService(INetworkModule _netModule)
	{
		try
		{
			Path absolutePath = Paths.get("../../TestFiles/FileMonitorService.txt");

			FileInputStream input = new FileInputStream(absolutePath.toString());
			InputStreamReader isr = new InputStreamReader(input);
			BufferedReader br = new BufferedReader(isr);

			try
			{
				while(true)
				{
					String line = br.readLine();
					
					if(line == null)
					{
						_netModule.writeLine("<EOF>");
						break;
					}
					else
					{
						_netModule.writeLine(line);
					}
				}
			}
			catch (IOException e)
			{
				_netModule.writeLine("<EOF>");

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
			_netModule.writeLine("<EOF>");
			System.out.println(String.format("파일을 찾을 수 없습니다."));
		}
	}
}