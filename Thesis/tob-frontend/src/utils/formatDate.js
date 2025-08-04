import dayjs from "dayjs";
import utc from "dayjs/plugin/utc";
import timezone from "dayjs/plugin/timezone";

dayjs.extend(utc);
dayjs.extend(timezone);

const LOCAL_TIMEZONE = "Europe/Bucharest";

export const formatDate = (dateInput) => {
  if (!dateInput) return "Not available";

  let date;

  if (Array.isArray(dateInput)) {
    const [year, month, day, hour, minute, second, nano] = dateInput;
    date = new Date(
      year,
      month - 1,
      day,
      hour,
      minute,
      second,
      Math.floor(nano / 1_000_000)
    );
  } else {
    date = new Date(dateInput);
  }

  return dayjs(date).tz(LOCAL_TIMEZONE).format("YYYY-MM-DD HH:mm:ss");
};
