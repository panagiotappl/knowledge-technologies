# Knowledge Technologies
## Homework 2

### Exercise 1
1.  “Translate” the following Greek sentences into ALCQO. If you think that
the given sentence cannot be translated into ALCQO, then you should give a
translation into first-order logic (remember: ALCQO, like all DLs we studied,
is a subset of first-order logic).
⊓⊑⇒≡¬∃ ⊔
(αʹ) Η Ελένη είναι όμορφη.
Beautiful(HELEN)

(βʹ) Ο Γιάννης είναι όμορφος και πλούσιος.
Beautiful(JOHN) ⊓ Rich(JOHN)

(γʹ) Ο Πέτρος είναι μυώδης και πλούσιος.
Muscular(PETER) ⊓ Rich(PETER)

(δʹ) Ο Τίμος είναι μυώδης και ευγενικός.
Muscular(TIM) ⊓ Kind(TIM)

(εʹ) Σε όλους τους άνδρες αρέσουν οι όμορφες γυναίκες.
Male ⊓ likes.(Beautiful(Woman))

(ϛʹ) ΄Ολοι οι πλούσιοι είναι ευτυχισμένοι.
Rich ⊑ Happy

(ζʹ) ΄Ολοι οι άνδρες που τους αρέσει μια γυναίκα, στην οποία αρέσουν, είναι
ευτυχισμένοι.
(∀x)((∃y)(Man(x) ⊓ Woman(y) ⊓ likes(x, y) ⊓ likes(y, x) ⇒ Happy(x))

(ηʹ) ΄Ολες οι γυναίκες που τους αρέσει ένας άνδρας, στον οποίο αρέσουν, είναι
ευτυχισμένες.
(∀x)((∃y)(Woman(x) ⊓ Man(y) ⊓ likes(x, y) ⊓ likes(y, x) ⇒ Happy(x))

(θʹ) Στην Κατερίνα αρέσουν όλοι οι άνδρες, στους οποίους η ίδια αρέσει.
(∀x)(likes(x, KATERINA) ⇒ likes(KATERINA, x))

(ιʹ) Στην Ελένη αρέσουν όλοι οι άνδρες που είναι ευγενικοί και πλούσιοι ή
μυώδεις και όμορφοι.
(∀x)(Man(x) ⊓ ((Muscular(x) ⊓ Beautiful(x)) ⊔ (Kind(x) ⊓ Beautiful(x))) ⇒ likes(HELEN, x))

(ιαʹ) Ο Κωστάκης, ο Γιωργάκης και η Ντορούλα είναι μέλη του πολιτικού κόμματος ΔΝΤ.
isPoliticalParty(DNT) ⊓ member
(ιβʹ) Κάθε μέλος του κόμματος ΔΝΤ που δεν είναι δεξιός, είναι φιλελεύθερος.

(ιγʹ) Στους δεξιούς δεν αρέσει ο σοσιαλισμός.

(ιδʹ) Σ΄ όποιον δεν αρέσει ο καπιταλισμός, δεν είναι φιλελεύθερος.

(ιεʹ) Στον Κωστάκη δεν αρέσει ό,τι αρέσει στον Γιωργάκη, και του αρέσει ό,τι
δεν αρέσει στον Γιωργάκη.

(ιϛʹ) Στο Γιωργάκη αρέσει ο σοσιαλισμός και ο καπιταλισμός.

(ιζʹ) Υπάρχει ένα μέλος του ΔΝΤ που είναι φιλελεύθερος αλλά δεν είναι δεξιός.

(ιηʹ) Δίποδο είναι ένα ζώο με ακριβώς δύο πόδια.

(ιθʹ) Τρίγωνο είναι ένα πολύγωνο που έχει ακριβώς τρεις γωνίες και ακριβώς
τρεις πλευρές που είναι ευθύγραμμα τμήματα.

(κʹ) Ορθογώνιο τρίγωνο είναι ένα τρίγωνο που μία από τις γωνίες του είναι
ορθή.