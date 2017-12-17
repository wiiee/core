namespace WebCore.Context
{
    using Platform.Context;
    using System;

    public class WebContextRepository : BaseContextRepository<WebContext>
    {
        private static readonly Lazy<WebContextRepository> _instance = new Lazy<WebContextRepository>(() => new WebContextRepository());

        public static WebContextRepository Instance
        {
            get
            {
                return _instance.Value;
            }
        }
    }
}
