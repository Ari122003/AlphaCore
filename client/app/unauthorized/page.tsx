import Link from "next/link";

interface UnauthorizedPageProps {
  searchParams: {
    required?: string;
    current?: string;
  };
}

export default function UnauthorizedPage({ searchParams }: UnauthorizedPageProps) {
  const required = searchParams?.required ?? "admin";
  const current = searchParams?.current ?? "none";

  return (
    <div className="min-h-screen bg-gradient-to-b from-violet-50 via-white to-white text-slate-900 transition-colors dark:from-[#070012] dark:via-[#04000c] dark:to-black">
      <main className="mx-auto flex max-w-2xl flex-col gap-6 px-6 py-20 text-center">
        <div className="space-y-4 rounded-[32px] border border-violet-200 bg-white/90 p-10 shadow-[0_25px_90px_rgba(99,57,255,0.2)] backdrop-blur-xl dark:border-violet-800/60 dark:bg-white/10">
          <p className="text-xs uppercase tracking-[0.4em] text-violet-600 dark:text-violet-200">Access blocked</p>
          <h1 className="text-4xl font-semibold text-[#210044] dark:text-white">Role mismatch</h1>
          <p className="text-base text-slate-600 dark:text-slate-100">
            You tried to open a portal reserved for the <span className="font-semibold">{required}</span> role, but your current
            session is tagged as <span className="font-semibold">{current}</span>.
          </p>
          <div className="rounded-2xl bg-violet-50/80 px-6 py-4 text-sm text-slate-600 dark:border dark:border-violet-800/60 dark:bg-white/10 dark:text-slate-100">
            Update the <span className="font-semibold">alpha-role</span> cookie (admin / coach / member) and reload to continue.
          </div>
          <div className="flex flex-wrap justify-center gap-3 text-sm">
            <Link
              href="/"
              className="rounded-full border border-violet-200/70 bg-white px-5 py-2 font-semibold text-violet-800 dark:border-violet-700/60 dark:bg-white/10 dark:text-white"
            >
              Back home
            </Link>
            <Link
              href="/admin"
              className="rounded-full border border-violet-200/70 px-5 py-2 text-violet-700 dark:border-violet-700/60 dark:text-violet-100"
            >
              Admin portal
            </Link>
            <Link
              href="/coach"
              className="rounded-full border border-violet-200/70 px-5 py-2 text-violet-700 dark:border-violet-700/60 dark:text-violet-100"
            >
              Coach portal
            </Link>
            <Link
              href="/member"
              className="rounded-full border border-violet-200/70 px-5 py-2 text-violet-700 dark:border-violet-700/60 dark:text-violet-100"
            >
              Member portal
            </Link>
          </div>
        </div>
      </main>
    </div>
  );
}
