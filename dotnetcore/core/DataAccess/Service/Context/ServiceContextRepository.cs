namespace Service.Context
{
    using Platform.Context;

    public class ServiceContextRepository : BaseContextRepository<ServiceContext>
    {
        public ServiceContext GetCurrent()
        {
            return GetContext() as ServiceContext;
            //return new ServiceContext("Service", "127.0.0.1");
        }
    }
}
