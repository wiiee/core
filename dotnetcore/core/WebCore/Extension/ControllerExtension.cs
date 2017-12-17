namespace WebCore.Extension
{
    using Microsoft.AspNetCore.Authentication;
    using Microsoft.AspNetCore.Authentication.Cookies;
    using Microsoft.AspNetCore.Mvc;
    using Platform.Data;
    using Platform.Util;
    using System.Collections.Generic;
    using System.Security.Claims;
    using System.Threading.Tasks;

    public static class ControllerExtension
    {
        public static string GetUserId(this Controller controller)
        {
            var user = controller.User;

            if (user == null || user.FindFirst(ClaimTypes.NameIdentifier) == null)
            {
                return null;
            }
            else
            {
                return user.FindFirst(ClaimTypes.NameIdentifier).Value;
            }
        }

        public static string GetUserName(this Controller controller)
        {
            var user = controller.User;

            if (user == null || user.FindFirst(ClaimTypes.Name) == null)
            {
                return string.Empty;
            }
            else
            {
                return user.FindFirst(ClaimTypes.Name).Value;
            }
        }

        public static bool IsAuthenticated(this Controller controller)
        {
            var user = controller.User;
            var result = user?.Identity?.IsAuthenticated;

            return result == null ? false : (bool)result;
        }

        public static void BuildHeaderMsg(this Controller controller, string successHeaderMsg, string errorHeaderMsg)
        {
            if (!string.IsNullOrEmpty(successHeaderMsg))
            {
                controller.ViewData["SuccessHeaderMsg"] = successHeaderMsg;
            }

            if (!string.IsNullOrEmpty(errorHeaderMsg))
            {
                controller.ViewData["ErrorHeaderMsg"] = errorHeaderMsg;
            }
        }

        public static bool IsMobileBrowser(this Controller controller)
        {
            return controller.Request.IsMobileBrowser();
        }

        public static void LogInCookie(this Controller controller, IUser user)
        {
            var claims = new List<Claim> {
                            new Claim(ClaimTypes.NameIdentifier, user.Id),
                            new Claim(ClaimTypes.UserData, JsonUtil.ToJson(user))
                        };

            var identity = new ClaimsIdentity(claims, "Password");
            var task = Task.Run(async () => { await controller.HttpContext.SignInAsync(CookieAuthenticationDefaults.AuthenticationScheme, new ClaimsPrincipal(identity)); });
            task.Wait();
        }

        public static void LogOffCookie(this Controller controller)
        {
            controller.HttpContext.SignOutAsync(CookieAuthenticationDefaults.AuthenticationScheme);
        }
    }
}
