using System;
using System.Threading;

namespace Platform.Context
{
    public abstract class BaseContextRepository<T> : IContextRepository
        where T : class, IContext
    {
        private ThreadLocal<T> contextHolder;

        public BaseContextRepository()
        {
            contextHolder = new ThreadLocal<T>();
        }

        public IContext GetContext()
        {
            return contextHolder.Value;
        }

        public void SetContext(IContext context)
        {
            contextHolder.Value = context as T;
        }
    }
}
