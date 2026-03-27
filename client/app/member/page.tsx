import Link from "next/link";
import { cookies } from "next/headers";

const memberModules = [
  {
    title: "Recovery timeline",
    body: "HRV, sleep, and readiness nudges curated from wearable integrations.",
  },
  {
    title: "Class compass",
    body: "Auto-generated weekly plan that adapts to attendance streaks and goals.",
  },
  {
    title: "Community pulse",
    body: "Micro-challenges with leaderboard snippets to keep motivation high.",
  },
];

export default function MemberPortalPage() {
  const role = cookies().get("alpha-role")?.value ?? "guest";

  return (
    <div className="min-h-screen bg-gradient-to-b from-violet-50 via-white to-white text-slate-900 transition-colors dark:from-[#070012] dark:via-[#04000c] dark:to-black">
      <main className="mx-auto flex max-w-4xl flex-col gap-8 px-6 py-14">
        <section className="space-y-6 rounded-[32px] border border-violet-200/70 bg-white/90 p-10 shadow-[0_25px_100px_rgba(103,71,255,0.15)] backdrop-blur-xl dark:border-violet-800/60 dark:bg-white/10">
          <div className="flex flex-wrap items-center justify-between gap-4">
            <div>
              <p className="text-xs uppercase tracking-[0.4em] text-violet-600 dark:text-violet-200">Member</p>
              <h1 className="mt-2 text-4xl font-semibold text-[#1c0038] dark:text-white">Journey cockpit</h1>
            </div>
            <span className="rounded-full border border-violet-200/70 px-4 py-2 text-xs uppercase tracking-[0.3em] text-violet-700 dark:border-violet-700/50 dark:text-violet-100">
              role: {role}
            </span>
          </div>
          <p className="text-base text-slate-600 dark:text-slate-100">
            Personalized scheduling, recovery, and progress loops designed for hybrid athletes who split their time across studios, outdoor sessions, and remote coaching.
          </p>
          <div className="grid gap-5 sm:grid-cols-3">
            {memberModules.map((module) => (
              <article
                key={module.title}
                className="rounded-2xl border border-violet-100 bg-white/80 p-5 text-slate-700 shadow-[0_12px_40px_rgba(103,71,255,0.12)] dark:border-violet-800/60 dark:bg-white/10 dark:text-white"
              >
                <h2 className="text-lg font-semibold text-[#1f0140] dark:text-white">{module.title}</h2>
                <p className="mt-2 text-sm text-slate-600 dark:text-slate-200">{module.body}</p>
              </article>
            ))}
          </div>
          <div className="rounded-2xl bg-violet-50/80 p-5 text-sm text-slate-600 dark:border dark:border-violet-800/60 dark:bg-white/10 dark:text-slate-100">
            This area is locked behind a role cookie. Set <span className="font-semibold">alpha-role=member</span> to preview the experience.
          </div>
          <div className="flex flex-wrap gap-3 text-sm">
            <Link
              href="/"
              className="rounded-full border border-violet-200/70 bg-white px-5 py-2 font-semibold text-violet-800 dark:border-violet-700/60 dark:bg-white/10 dark:text-white"
            >
              Back to marketing site
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
          </div>
        </section>
      </main>
    </div>
  );
}
