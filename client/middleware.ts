import { NextResponse } from "next/server";
import type { NextRequest } from "next/server";

const roleMatrix: Record<string, string> = {
  "/admin": "admin",
  "/coach": "coach",
  "/member": "member",
};

export function middleware(request: NextRequest) {
  const { pathname } = request.nextUrl;

  const protectedEntry = Object.entries(roleMatrix).find(([route]) => {
    return pathname === route || pathname.startsWith(`${route}/`);
  });

  if (!protectedEntry) {
    return NextResponse.next();
  }

  const [, requiredRole] = protectedEntry;
  const sessionRole = request.cookies.get("alpha-role")?.value;

  if (sessionRole === requiredRole) {
    return NextResponse.next();
  }

  const redirectUrl = new URL("/unauthorized", request.url);
  redirectUrl.searchParams.set("required", requiredRole);
  if (sessionRole) {
    redirectUrl.searchParams.set("current", sessionRole);
  }

  return NextResponse.redirect(redirectUrl);
}

export const config = {
  matcher: ["/admin/:path*", "/coach/:path*", "/member/:path*"],
};
