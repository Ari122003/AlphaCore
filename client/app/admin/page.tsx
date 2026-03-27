import Link from "next/link";
import { cookies } from "next/headers";

const adminControls = [
  {
    name: "Network overview",
    detail: "Live KPIs for every studio, locker rental utilization, and cash flow forecasts.",
  },
  {
    name: "Access orchestration",
    detail: "Create passes, freeze memberships, and trigger automated renewal journeys.",
  },
  {
    name: "Retail pulse",
    detail: "Bundle supplements, apparel, and recovery services with instant pricing pushes.",
  },
];

export default function AdminPortalPage() {
  const role = cookies().get("alpha-role")?.value ?? "guest";

  return (
    <div className="min-h-screen bg-gradient-to-b from-violet-50 via-white to-white text-slate-900 transition-colors dark:from-[#070012] dark:via-[#04000c] dark:to-black">
      <main className="mx-auto flex max-w-5xl flex-col gap-12 px-6 py-16">
        <div className="space-y-6 rounded-3xl border border-violet-200/70 bg-white/85 p-10 shadow-[0_35px_120px_rgba(99,57,255,0.18)] backdrop-blur-xl dark:border-violet-800/60 dark:bg-white/10">
          <div className="flex flex-wrap items-center justify-between gap-4">
            <div>
              <p className="text-xs uppercase tracking-[0.4em] text-violet-600 dark:text-violet-200">Gym admin</p>
              <h1 className="mt-2 text-4xl font-semibold text-[#200142] dark:text-white">Command hub</h1>
            </div>
            <span className="rounded-full border border-violet-200/70 px-4 py-2 text-sm text-violet-700 dark:border-violet-700/50 dark:text-violet-100">
              Active role: <strong className="ml-1 font-semibold">{role}</strong>
            </span>
          </div>
          <p className="text-lg text-slate-600 dark:text-slate-100">
            Monitor every revenue stream, deploy new programs, and keep compliance wrapped up in minutes. This view unifies
            scheduling, billing, access control, and inventory signals for multi-site gym operators.
          </p>
          <div className="grid gap-4 sm:grid-cols-3">
            {adminControls.map((control) => (
              <article
                key={control.name}
                className="rounded-2xl border border-violet-100 bg-white/70 p-5 text-slate-700 shadow-[0_15px_45px_rgba(123,65,255,0.12)] dark:border-violet-800/60 dark:bg-white/10 dark:text-white"
              >
                <h2 className="text-xl font-semibold text-[#230248] dark:text-white">{control.name}</h2>
                <p className="mt-2 text-sm text-slate-600 dark:text-slate-200">{control.detail}</p>
              </article>
            ))}
          </div>
          <p className="rounded-2xl border border-violet-100 bg-violet-50/70 px-5 py-4 text-sm text-slate-600 dark:border-violet-800/60 dark:bg-white/10 dark:text-slate-100">
            Need to impersonate this view? Set the <span className="font-semibold">alpha-role</span> cookie to
            <span className="font-semibold"> admin</span> (e.g. in devtools) and refresh.
          </p>
          <div className="flex flex-wrap gap-3 text-sm">
            <Link
              href="/"
              className="rounded-full border border-violet-200/70 bg-white px-5 py-2 font-semibold text-violet-800 dark:border-violet-700/60 dark:bg-white/10 dark:text-white"
            >
              Back to marketing site
            </Link>
            <Link
              href="/coach"
              className="rounded-full border border-violet-200/70 px-5 py-2 text-violet-700 dark:border-violet-700/60 dark:text-violet-100"
            >
              Switch to coach view
            </Link>
          </div>
        </div>
      </main>
    </div>
  );
}
