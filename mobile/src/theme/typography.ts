import { TextStyle } from "react-native";

export const FONTS = {
  title: {
    fontSize: 48,
    fontWeight: "bold" as TextStyle["fontWeight"],
    letterSpacing: 2,
  },
  subtitle: {
    fontSize: 28,
    fontWeight: "bold" as TextStyle["fontWeight"],
  },
  heading: {
    fontSize: 22,
    fontWeight: "bold" as TextStyle["fontWeight"],
  },
  body: {
    fontSize: 16,
  },
  label: {
    fontSize: 14,
    fontWeight: "600" as TextStyle["fontWeight"],
    textTransform: "uppercase" as TextStyle["textTransform"],
  },
  caption: {
    fontSize: 12,
  },
};
