package deu.java002_02.study.ni;

import deu.java002_02.study.main.IService;

public interface INetworkService extends IService
{
	void bindNetworkModule(INetworkModule _netModule);
}
