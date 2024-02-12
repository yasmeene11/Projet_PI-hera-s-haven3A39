<?php

namespace App\Form;

use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\CheckboxType;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Component\Form\Extension\Core\Type\HiddenType; // Import HiddenType
use Symfony\Component\Form\Extension\Core\Type\PasswordType;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class RegisterType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options)
    {
        $builder
            ->add('Name', TextType::class, [
                'label' => 'Name',
                'attr' => [
                    'placeholder' => 'Enter your name',
                ],
            ])
            ->add('Surname', TextType::class, [
                'label' => 'Surname',
                'attr' => [
                    'placeholder' => 'Enter your surname',
                ],
            ])
            ->add('Gender', ChoiceType::class, [
                'label' => 'Gender',
                'choices' => [
                    'Male' => 'male',
                    'Female' => 'female',
                ],
            ])
            ->add('Phone_Number', TextType::class, [
                'label' => 'Phone Number',
                'attr' => [
                    'placeholder' => 'Enter your phone number',
                ],
            ])
            ->add('Address', TextType::class, [
                'label' => 'Address',
                'attr' => [
                    'placeholder' => 'Enter your address',
                ],
            ])
            ->add('Email', TextType::class, [
                'label' => 'Email',
                'attr' => [
                    'placeholder' => 'Enter your email',
                ],
            ])
            ->add('Password', PasswordType::class, [
                'label' => 'Password',
                'attr' => [
                    'placeholder' => '•••••••••••••',
                ],
            ])
            ->add('Role', ChoiceType::class, [
                'label' => 'Role',
                'choices' => [
                    'Admin' => 'admin',
                    'Veterinary' => 'veterinary',
                ],
            ])
            ->add('Account_Status', HiddenType::class, [
                'data' => 'Waiting for admin approval', // Set default value
            ]);
    }

    public function configureOptions(OptionsResolver $resolver)
    {
        $resolver->setDefaults([
            // Configure your form options here
        ]);
    }
}
