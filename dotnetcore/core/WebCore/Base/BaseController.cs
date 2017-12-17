namespace WebCore.Base
{
    using Microsoft.AspNetCore.Mvc;
    using Microsoft.Extensions.Logging;
    using Platform.Util;

    public abstract class BaseController : Controller
    {
        private static ILogger _logger = LoggerUtil.CreateLogger<BaseController>();

        //public UserService _userService;
        //public PreferenceService _preferenceService;
        //public ProductService _productService;
    }
}
