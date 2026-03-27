import Link from "next/link";
import { cookies } from "next/headers";

const coachStreams = [
  {
    label: "Roster radar",
    description: "Assign readiness colors, chat with athletes, and auto-adjust loads in one swipe.",
  },
  {
    label: "Session builder",
    description: "Drag blocks, tempo work, and conditioning ladders pulled from AlphaCore templates.",
  },
  {
    label: "Signal lab",
    description: "See chronic vs. acute workloads, soft tissue alerts, and attendance deltas.",
  },
];

export default function CoachPortalPage() {
  const role = cookies().get("alpha-role")?.value ?? "guest";

  return (
    <div className="min-h-screen bg-gradient-to-b from-violet-50 via-white to-white text-slate-900 transition-colors dark:from-[#070012] dark:via-[#04000c] dark:to-black">
      <main className="mx-auto flex max-w-5xl flex-col gap-10 px-6 py-16">
        <section className="space-y-6 rounded-[30px] border border-violet-200/70 bg-white/85 p-10 shadow-[0_30px_120px_rgba(99,57,255,0.18)] backdrop-blur-xl dark:border-violet-800/60 dark:bg-white/10">
          <div className="flex flex-wrap items-center justify-between gap-4">
            <div>
              <p className="text-xs uppercase tracking-[0.4em] text-emerald-500 dark:text-emerald-200">Coach</p>
              <h1 className="mt-2 text-4xl font-semibold text-[#200142] dark:text-white">Performance console</h1>
            </div>
            <span className="rounded-full border border-violet-200/70 px-4 py-2 text-sm text-violet-700 dark:border-violet-700/50 dark:text-violet-100">
              role: {role}
            </span>
          </div>
          <p className="text-base text-slate-600 dark:text-slate-100">
            Streamline roster management, programming updates, and athlete communication with a cockpit tuned to busy coaching teams.
          </p>
          <div className="grid gap-5 md:grid-cols-3">
            {coachStreams.map((stream) => (
              <article
                key={stream.label}
                className="rounded-2xl border border-violet-100 bg-white/75 p-5 text-slate-700 shadow-[0_15px_45px_rgba(123,65,255,0.12)] dark:border-violet-800/60 dark:bg-white/10 dark:text-white"
              >
                <h2 className="text-xl font-semibold text-[#230248] dark:text-white">{stream.label}</h2>
                <p className="mt-3 text-sm text-slate-600 dark:text-slate-200">{stream.description}</p>
              </article>
            ))}
          </div>
          <p className="rounded-2xl border border-violet-100 bg-violet-50/70 px-5 py-4 text-sm text-slate-600 dark:border-violet-800/60 dark:bg-white/10 dark:text-slate-100">
            Grant access by setting <span className="font-semibold">alpha-role=coach</span>. Middleware keeps other roles out of this workspace.
          </p>
          <div className="flex flex-wrap gap-3 text-sm">
            <Link
              href="/"
              className="rounded-full border border-violet-200/70 bg-white px-5 py-2 font-semibold text-violet-800 dark:border-violet-700/60 dark:bg-white/10 dark:text-white"
            >
              Marketing site
            </Link>
            <Link
              href="/member"
              className="rounded-full border border-violet-200/70 px-5 py-2 text-violet-700 dark:border-violet-700/60 dark:text-violet-100"
            >
              Member view
            </Link>
          </div>
        </section>
      </main>
    </div>
  );
}
