namespace WebCore.Base
{
    using DataAccess.Service;
    using Microsoft.AspNetCore.Http;
    using Microsoft.AspNetCore.Mvc;
    using Platform.Data;
    using Platform.Util;
    using WebCore.Extension;

    public abstract class BaseAccountController : BaseController
    {
        private IUserService userService;

        public BaseAccountController(IUserService userService)
        {
            this.userService = userService;
        }

        // GET: /<controller>/
        public IActionResult Login(string id, string password, string returnUrl)
        {
            if (!string.IsNullOrWhiteSpace(id))
            {
                id = id.Trim();

                if (ModelState.IsValid)
                {
                    var result = userService.LogIn(id, password);

                    if (result.IsSuccessful)
                    {
                        this.LogInCookie(userService.Get(id));

                        // 如果是注册或者登陆页面，跳转到首页
                        if (returnUrl != null &&
                            (returnUrl.ToLower().Contains("account/login") ||
                            returnUrl.ToLower().Contains("account/signup")))
                        {
                            returnUrl = null;
                        }
                    }
                    else
                    {
                        ViewBag.ErrorMsg = result.Msg;
                        ViewBag.ReturnUrl = returnUrl;

                        return View();
                    }
                }
            }
            else
            {
                if (string.IsNullOrEmpty(returnUrl) || returnUrl.Equals("/"))
                {
                    returnUrl = HttpContext.Request.PathBase;
                }
                ViewBag.ReturnUrl = returnUrl;
                return View();
            }

            return Redirect(UrlUtil.GetRelativeUrl(returnUrl));
        }

        public IActionResult SignUp(IUser user, string returnUrl)
        {
            if (user != null && !string.IsNullOrEmpty(user.Id))
            {
                if (ModelState.IsValid)
                {
                    var result = userService.SignUp(user);

                    if (result.IsSuccessful)
                    {
                        this.LogInCookie(user);

                        //如果是注册或者登陆页面，跳转到首页
                        if (returnUrl != null &&
                            (returnUrl.ToLower().Contains("account/login") ||
                            returnUrl.ToLower().Contains("account/signup")))
                        {
                            returnUrl = null;
                        }
                    }
                    else
                    {
                        ViewBag.ErrorMsg = result.Msg;
                        ViewBag.ReturnUrl = returnUrl;
                        return View();
                    }
                }
            }
            else
            {
                if (string.IsNullOrEmpty(returnUrl) || returnUrl.Equals("/"))
                {
                    returnUrl = HttpContext.Request.PathBase;
                }
                ViewBag.ReturnUrl = returnUrl;
                return View();
            }

            return Redirect(UrlUtil.GetRelativeUrl(returnUrl));
        }

        public IActionResult LogOff(string returnUrl)
        {
            this.LogOffCookie();
            return Redirect(UrlUtil.GetRelativeUrl(returnUrl));
        }
    }
}
