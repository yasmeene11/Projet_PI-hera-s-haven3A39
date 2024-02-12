<?php

namespace App\Form;

use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use App\Entity\Account;
use App\Entity\Animal;
use App\Entity\Appointment;
use Symfony\Component\Form\Extension\Core\Type\DateType;
use Symfony\Component\Form\Extension\Core\Type\TimeType;
use Symfony\Component\Form\FormEvents;
use Symfony\Component\Form\FormEvent;

class AppointmentType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options)
    {
        $builder
        ->add('Account_Key', EntityType::class, [
            'class' => Account::class,
            'choice_label' => function (Account $account) {
                // Use getaccountId() here instead of getId()
                return sprintf('%s %s (ID: %d)', $account->getName(), $account->getSurname(), $account->getaccountId());
            },
            'label' => 'User',
            'required' => true,
        ])
            ->add('Animal_Key', EntityType::class, [
                'class' => Animal::class,
                'choice_label' => function (Animal $animal) {
                    return sprintf('%s - %s (ID: %d)', $animal->getAnimalName(), $animal->getAnimalBreed(), $animal->getanimalId());
                },
                'label' => 'Animal',
                'required' => true,
            ])
            ->add('Appointment_Date', DateType::class, [
                'label' => 'Appointment Date',
                'widget' => 'single_text',
                'required' => true,
            ])
            ->add('Appointment_Time', TimeType::class, [
                'label' => 'Appointment Time',
                'widget' => 'single_text',
                'required' => true,
            ]);

        // Listen to form PRE_SET_DATA event to conditionally add the Appointment_Status field
        $builder->addEventListener(FormEvents::PRE_SET_DATA, function (FormEvent $event) use ($options) {
            $form = $event->getForm();
            $appointment = $event->getData(); // Get your Appointment entity

            // Conditionally add the Appointment_Status field based on your custom option or logic
            if ($options['include_status_field']) {
                $form->add('Appointment_Status', ChoiceType::class, [
                    'choices' => [
                        'Pending' => 'pending',
                        'Confirmed' => 'confirmed',
                        'Cancelled' => 'cancelled',
                    ],
                    'label' => 'Appointment Status',
                    'required' => true,
                ]);
            }
        });
    }

    public function configureOptions(OptionsResolver $resolver)
    {
        $resolver->setDefaults([
            'data_class' => Appointment::class,
            // Add the custom option with a default value
            'include_status_field' => false, // Default to false, adjust as needed
        ]);
    }
}
