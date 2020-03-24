(ns ch8.core
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

(defmacro infix
  "Infix macro"
  [infixed]
  (list (second infixed) (first infixed) (last infixed)))

(defmacro postfix
  [postfixed]
  (reverse postfixed))

(postfix (1 2 3 +))

(eval  (macroexpand '(postfix (1 2 3 +))))

(infix (2 + 3))


;; Unquoting with ~ causes (good and bad) to resolve to
;; the corresponding values (not evaluated)
(defmacro code-critic
  "Phrases are courtesy Hermes Conrad from Futurama"
  [bad good]
  `(do (println "Great squid of Madrid, this is bad code:"
                (quote ~bad))
       (println "Sweet gorilla of Manila, this is good code:"
                (quote ~good))))

(macroexpand '(code-critic (1 + 1) (+ 1 1)))


;;(eval `(+ ~(list 1 2 3)))
;; Cast Error
;; ~(list 1 2 3) ==> (1 2 3) ==> Clojure eval => error

(eval `(+ ~@(list 1 2 3)))
;; => 6
;; Unquoate Splicing (~@) causes
;; `(+ ~@(list 1 2 3)) => `(+ 1 2 3)

(eval `(+ 1 2 3))


;; validation macro
;;  {:name "Mitchard Blimmons"
(def order-details
  {:name "Mitchard Blimmons"
   :email "mitchard.blimmons@gmail.com"})


(def order-details-validations
  {:name   ["Please enter a name" not-empty]
   :email  ["Please enter an email address"
            not-empty
            "Your email address doesn't look like an email address"
            #(or (empty? %) (re-seq #"@" %))]})

(defn error-messages-for
  "Return a seq of error messages"
  [to-validate message-validator-pairs]
  (map first (filter #(not ((second %) to-validate))
                     (partition 2 message-validator-pairs))))

(defn validate
  "Returns a map with a vector of errors for each key"
  [to-validate validations]
  (reduce (fn [errors validation]
              (let [[fieldname validation-check-groups] validation
                 value (get to-validate fieldname)
                 error-messages (error-messages-for value validation-check-groups)]
                (if (empty? error-messages)
                  errors
                  (assoc errors fieldname error-messages))))
          {}
          validations))


;; common usage
(let [errors (validate order-details order-details-validations)]
    (if (empty? errors)
          (println :success)
          (println :failure errors)))

;; if-valid macro definition
(defmacro if-valid
  "Handle validation more concisely"
  [to-validate validations errors-name & then-else]
  `(let [~errors-name (validate ~to-validate ~validations)]
     (if (empty? ~errors-name)
       ~@then-else)))

;; if-valid macro use
(if-valid order-details order-details-validations errors
          (println :success)
          (println :failure errors))

(macroexpand '(if-valid order-details order-details-validations my-error-name
                  (println :success)
                  (println :failure my-error-name)))


(when (= 1 1)
  (println 1)
  (println 2)
  (println 3))

;; ex 1
;; when-valid macro definition
(defmacro when-valid
  "Handle validation more concisely"
  [to-validate validations errors-name & then]
  `(let [~errors-name (validate ~to-validate ~validations)]
     (when (empty? ~errors-name)
       ~@then)))


(when-valid order-details order-details-validations errors
          (println :success)
          (println (str "success")))

;; ex:2 implement or macro
(defmacro or
  "Returns first truthy. Shortcircuits when an expr is true."
  ([] nil)
  ([x] x)
  ([x & next]
   `(let [or# ~x]
      (if or# or# (or ~@next)))))


(or nil false)


;; ex 3
(def character {:name "Shinto"
                :attributes {:intelligence 10
                             :strength 4
                             :dexterity 7}})

(println character)

(defmacro defattr
  [[nam attr]]
  `(def ~nam #((comp ~attr :attributes) %)))

(macroexpand '(defattr [x-int :intelligence]))
(defattr '(x-int :intelligence))
(x-int character)

(defmacro defattrs
  [& namekeypairs]
  (let [nkps (partition 2 namekeypairs)]
    `(do
      ~@(map (fn [namekeypair] (list 'defattr namekeypair)) nkps))))

(macroexpand (defattrs c-int :intelligence
          c-str :strength
          c-dex :dexterity))

(println (c-int character)
  (c-str character)
  (c-dex character))

(macroexpand '(doseq-macro report (= 1 1) (= 1 2)))

(defmacro report  
  [to-try]  
  `(let [result# ~to-try]     
     (if result#       
       (println (quote ~to-try) "was successful:" result#)       
       (println (quote ~to-try) "was not successful:" result#))))

(defmacro doseq-macro
  [macroname & args]
  `(do
     ~@(map (fn [arg] (list macroname arg)) args)))

(macroexpand '(doseq-macro report (= 1 1) (= 1 2)))

(map #(apply + %) '((1 2) (2 3)))
