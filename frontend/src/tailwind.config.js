/** @type {import('tailwindcss').Config} */
module.exports = {
   content: [
     "./src/**/*.{js,jsx,ts,tsx}", // ✅ Ensure Tailwind scans all your JSX components
     "./public/index.html"
   ],
   theme: {
     extend: {},
   },
   plugins: [],
 };
 