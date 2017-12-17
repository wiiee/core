namespace Service
{
    using Context;
    using Microsoft.Extensions.Logging;
    using Platform.Util;
    using System;
    using System.Collections.Generic;

    //Resolve Service之间互相引用导致死循环的问题
    public class ServiceFactory
    {
        private static ILogger _logger = LoggerUtil.CreateLogger<ServiceFactory>();
        private Dictionary<string, IService> services;

        private ServiceFactory()
        {
            services = new Dictionary<string, IService>();
        }

        private static readonly Lazy<ServiceFactory> lazy = new Lazy<ServiceFactory>(() => new ServiceFactory());

        public static ServiceFactory Instance { get { return lazy.Value; } }

        public T GetService<T>()
            where T : IService
        {
            try
            {
                Type type = typeof(T);
                var key = type.Name;

                if (!services.ContainsKey(key))
                {
                    services.Add(key, (T)Activator.CreateInstance(type, ServiceContextUtil.SERVICE_CONTEXT));
                }

                return (T)services[key];
            }
            catch (Exception ex)
            {
                _logger.LogError(ex.Message);
                return default(T);
            }
        }
    }
}
